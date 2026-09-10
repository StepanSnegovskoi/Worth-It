package com.metes.worthit.core.database.converter

import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals

class LocalDateConverterTest {

    private lateinit var converter: LocalDateConverter

    @Before
    fun setup() {
        converter = LocalDateConverter()
    }

    @Test
    fun `localDateToEpochDay calculates epoch days correctly`() {
        val date = LocalDate.of(2026, 9, 9)
        assertEquals(20705L, converter.localDateToEpochDay(date))
    }

    @Test
    fun `localDateToEpochDay calculates epoch days correctly for days before unix epoch`() {
        val date = LocalDate.of(1000, 9, 9)
        assertEquals(-354034L, converter.localDateToEpochDay(date))
    }

    @Test
    fun `localDateToEpochDay calculates epoch days correctly for start day of unix epoch`() {
        val date = LocalDate.of(1970, 1, 1)
        assertEquals(0L, converter.localDateToEpochDay(date))
    }

    @Test
    fun `epochDayToLocalDate calculates LocalDate correctly for date after start of unix epoch`() {
        val epochDays = 20705L
        val date = LocalDate.of(2026, 9, 9)
        assertEquals(date, converter.epochDayToLocalDate(epochDays))
    }

    @Test
    fun `epochDayToLocalDate calculates LocalDate correctly for date before start of unix epoch`() {
        val epochDays = -7L
        val date = LocalDate.EPOCH.plus(epochDays, ChronoUnit.DAYS)
        assertEquals(date, converter.epochDayToLocalDate(epochDays))
    }

    @Test
    fun `epochDayToLocalDate calculates LocalDate correctly for start day of unix epoch`() {
        assertEquals(LocalDate.EPOCH, converter.epochDayToLocalDate(0))
    }
}
