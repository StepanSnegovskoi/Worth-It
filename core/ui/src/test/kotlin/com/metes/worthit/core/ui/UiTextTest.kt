package com.metes.worthit.core.ui

import org.junit.Assert.assertEquals
import org.junit.Test
import android.content.Context
import com.metes.worthit.core.presentation.UiText
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UiTextTest {

    private val context: Context = mockk()

    @Test
    fun `asString with DynamicString returns exact string without interacting with Context`() {
        val expected = "Network timeout"
        val uiText = UiText.DynamicString(expected)

        val actual = uiText.asString(context)

        assertEquals(expected, actual)
        verify(exactly = 0) { context.getString(any()) }
    }

    @Test
    fun `asString with StringResource passes unpacked arguments array to Context`() {
        val resId = 1
        val arg1 = "Item"
        val arg2 = 42
        val expectedResult = "Item with count 42"

        every { context.getString(resId, arg1, arg2) } returns expectedResult

        val uiText = UiText.StringResource(
            resId = resId,
            args = listOf(arg1, arg2),
        )

        val actual = uiText.asString(context)

        assertEquals(expectedResult, actual)
        verify(exactly = 1) { context.getString(resId, arg1, arg2) }
    }
}
