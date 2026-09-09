package com.metes.worthit.core.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.metes.worthit.core.data.utils.ImageCompressor
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

@RunWith(RobolectricTestRunner::class)
class InternalStorageImplTest {

    private val schedule = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(schedule)
    private lateinit var context: Context
    private lateinit var internalStorage: StorageRepository
    private lateinit var imageCompressor: ImageCompressor

    @Before
    fun setup() {
        imageCompressor = mockk()
        context = ApplicationProvider.getApplicationContext()
        internalStorage = InternalStorageImpl(
            context = context,
            ioDispatcher = dispatcher,
            imageCompressor = imageCompressor,
        )
    }

    @After
    fun teardown() {
        clearMocks(imageCompressor)
        context.cacheDir.deleteRecursively()
    }

    @Test
    fun `saveImage with content scheme calls compressor and returns path on success`() = runTest(dispatcher) {
        val dummyUriString = "content://media/external/images/media/1"
        val fileName = "test_image"
        val expectedFile = File(context.filesDir, "images/$fileName.webp")

        coEvery {
            imageCompressor.compress(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } answers {
            expectedFile.parentFile?.mkdirs()
            expectedFile.createNewFile()
            Result.Success(Unit)
        }

        val saveResult = internalStorage.saveImage(
            imagePath = dummyUriString,
            fileName = fileName,
        )

        assertTrue(saveResult is Result.Success)
        assertEquals(expectedFile.absolutePath, saveResult.data)
    }

    @Test
    fun `deleteFile deletes file correctly`() = runTest(dispatcher) {
        val file = File(context.cacheDir, Uuid.random().toString()).apply {
            createNewFile()
        }
        assertTrue(file.exists())

        internalStorage.deleteFile(file.absolutePath)
        assertFalse(file.exists())
    }
}