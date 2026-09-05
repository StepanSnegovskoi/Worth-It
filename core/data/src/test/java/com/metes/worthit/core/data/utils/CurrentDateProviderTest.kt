package com.metes.worthit.core.data.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import app.cash.turbine.test
import com.metes.worthit.core.domain.utils.DateProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentDateProviderTest {

    private var testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private var testScope: CoroutineScope = CoroutineScope(testDispatcher)

    private lateinit var dateProvider: DateProvider
    private lateinit var clock: Clock
    private lateinit var context: Context

    @Before
    fun setup() {
        clock = mockk(relaxed = true)
        context = mockk(relaxed = true)

        every { clock.instant() } returns Instant.parse("2026-09-05T10:00:00Z")
        every { clock.zone } returns ZoneId.of("UTC")

        mockkConstructor(IntentFilter::class)
        mockkStatic(ContextCompat::class)

        dateProvider = CurrentDateProvider(
            context = context,
            applicationScope = testScope,
            clock = clock
        )
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `CurrentDateProvider returns correct date`() = runTest {
        val receiverSlot = slot<BroadcastReceiver>()

        every {
            ContextCompat.registerReceiver(
                any(),
                capture(receiverSlot),
                any(),
                any(),
            )
        } returns mockk()

        val expectedDate = LocalDate.of(2026, 9, 5)
        dateProvider.currentDateFlow.test {
            // initial day
            val initialDate = awaitItem()
            assertEquals(expectedDate, initialDate)

            every { clock.instant() } returns Instant.parse("2026-09-06T10:00:00Z")
            receiverSlot.captured.onReceive(context, Intent(Intent.ACTION_TIME_CHANGED))

            // added 1 day
            assertEquals(expectedDate.plus(1, ChronoUnit.DAYS), awaitItem())

            every { clock.instant() } returns Instant.parse("2026-09-07T10:00:00Z")
            receiverSlot.captured.onReceive(context, Intent(Intent.ACTION_DATE_CHANGED))

            // added 2 days
            assertEquals(expectedDate.plus(2, ChronoUnit.DAYS), awaitItem())

            every { clock.instant() } returns Instant.parse("2026-09-08T10:00:00Z")
            receiverSlot.captured.onReceive(context, Intent(Intent.ACTION_TIMEZONE_CHANGED))

            // added 3 days
            assertEquals(expectedDate.plus(3, ChronoUnit.DAYS), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `CurrentDateProvider should include three necessary actions in IntentFilter`() = runTest {
        every {
            anyConstructed<IntentFilter>().addAction(any())
        } returns Unit

        dateProvider.currentDateFlow.test {
            verify(exactly = 3) { IntentFilter().addAction(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }
}