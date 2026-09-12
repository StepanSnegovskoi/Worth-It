package com.metes.worthit.core.domain.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class throwIfNullTest {

    private val exception = IllegalArgumentException("Test exception")
    @Test
    fun `throwIfNull should throw provided exception when receiver is null`() {
        val nullableNumber: Int? = null

        try {
            nullableNumber.throwIfNull(exception)
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
            assertEquals(e.message, exception.message)
        }
    }

    @Test
    fun `throwIfNull should not throw provided exception when receiver is not null`() {
        val nullableNumber: Int? = 1
        nullableNumber.throwIfNull(exception)
    }
}
