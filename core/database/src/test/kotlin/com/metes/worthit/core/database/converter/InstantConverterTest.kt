package com.metes.worthit.core.database.converter

import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertEquals

class InstantConverterTest {

    private lateinit var converter: InstantConverter

    @Before
    fun setup() {
        converter = InstantConverter()
    }

    @Test
    fun `instantToEpochMilli calculates positive millis correctly`() {
        val millis = 1_000_000_000L
        assertEquals(millis, converter.instantToEpochMilli(Instant.ofEpochMilli(millis)))
    }


    @Test
    fun `instantToEpochMilli calculates negative millis correctly`() {
        val millis = -1_000_000_000L
        assertEquals(millis, converter.instantToEpochMilli(Instant.ofEpochMilli(millis)))
    }

    @Test
    fun `instantToEpochMilli calculates zero millis correctly`() {
        val millis = 0L
        assertEquals(millis, converter.instantToEpochMilli(Instant.ofEpochMilli(millis)))
    }

    @Test
    fun `epochMilliToInstant calculates correctly for positive millis`() {
        val millis = 1_000_000_000L
        assertEquals(Instant.ofEpochMilli(millis), converter.epochMilliToInstant(millis))
    }


    @Test
    fun `epochMilliToInstant calculates correctly for negative millis`() {
        val millis = -1_000_000_000L
        assertEquals(Instant.ofEpochMilli(millis), converter.epochMilliToInstant(millis))
    }

    @Test
    fun `epochMilliToInstant calculates correctly for zero millis`() {
        val millis = 0L
        assertEquals(Instant.ofEpochMilli(millis), converter.epochMilliToInstant(millis))
    }
}
