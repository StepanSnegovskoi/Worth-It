package com.metes.worthit.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.util.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalDateExtensionTest {

    @Test
    fun `toUtcEpochMilli converts LocalDate to correct epoch milliseconds`() {
        val date = LocalDate.of(2026, Month.SEPTEMBER, 4)
        println(date.toUtcEpochMilli())
        assertEquals(1_788_480_000_000L, date.toUtcEpochMilli())
    }

    @Test
    fun `toUtcEpochMilli returns 0 for Unix epoch start date`() {
        val date = LocalDate.EPOCH
        assertEquals(0L, date.toUtcEpochMilli())
    }

    @Test
    fun `toUtcEpochMilli returns negative millis for dates before 1970`() {
        val date = LocalDate.of(1969, Month.JANUARY, 1)
        assertEquals(-31_536_000_000L, date.toUtcEpochMilli())
    }

    @Test
    fun `toUtcEpochMilli is independent of default system timezone`() {
        val date = LocalDate.of(2026, Month.JANUARY, 1)
        val originalZone = TimeZone.getDefault()

        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
            val tokyoResult = date.toUtcEpochMilli()

            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
            val newYorkResult = date.toUtcEpochMilli()

            assertEquals(tokyoResult, newYorkResult)
            assertEquals(1_767_225_600_000L, newYorkResult)
        } finally {
            TimeZone.setDefault(originalZone)
        }
    }

    @Test
    fun `toUtcEpochMilli converts back to the same LocalDate in UTC`() {
        val date = LocalDate.of(2026, Month.JANUARY, 1)
        val millis = date.toUtcEpochMilli()

        val restoredDate = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
        assertEquals(date, restoredDate)
    }

    @Test
    fun `toLocalDateFromUtc converts millis to correct LocalDate`() {
        val millis = 1_788_480_000_000L
        val expectedDate = LocalDate.of(2026, Month.SEPTEMBER, 4)

        val actualDate = millis.toLocalDateFromUtc()
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `toLocalDateFromUtc is independent of current timezone`() {
        val millis = 1_788_480_000_000L
        val originalTimezone = TimeZone.getDefault()

        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC+11"))
            val utcPlus11Date = millis.toLocalDateFromUtc()

            TimeZone.setDefault(TimeZone.getTimeZone("UTC-33"))
            val utcMinus33Date = millis.toLocalDateFromUtc()

            assertEquals(utcMinus33Date, utcPlus11Date)
        } finally {
            TimeZone.setDefault(originalTimezone)
        }
    }

    @Test
    fun `toLocalDateFromUtc returns correct date before 1970 when millis is negative`() {
        val date = LocalDate.of(1969, Month.JANUARY, 1)
        val millis = -31_536_000_000L
        assertEquals(date, millis.toLocalDateFromUtc())
    }

    @Test
    fun `toLocalDateFromUtc converts back to the same millis in UTC`() {
        val millis = 1_788_480_000_000L
        val date = millis.toLocalDateFromUtc()
        val restoredMillis = Instant.ofEpochMilli(
            date.atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        ).toEpochMilli()

        assertEquals(millis, restoredMillis)
    }

    @Test
    fun `toLocalDateFromUtc returns correct date for 0 millis after Unix start date`() {
        val dateEpoch = LocalDate.EPOCH
        val millis = 0L
        val date = millis.toLocalDateFromUtc()

        assertEquals(dateEpoch, date)
    }
}
