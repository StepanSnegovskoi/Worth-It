package com.metes.worthit.core.domain.entity

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeUnitTest {

    @Test
    fun `between returns correct count of time units`() {
        val firstDate = LocalDate.of(2026, 5, 10)
        val secondDate = LocalDate.of(2023, 3, 12)
        assertEquals(1156, TimeUnit.DAY.between(secondDate, firstDate))
        assertEquals(166, TimeUnit.WEEK.between(secondDate, firstDate))
        assertEquals(38, TimeUnit.MONTH.between(secondDate, firstDate))
        assertEquals(4, TimeUnit.YEAR.between(secondDate, firstDate))
    }

    @Test
    fun `between returns 1 for the same dates`() {
        val firstDate = LocalDate.of(2026, 5, 10)
        val secondDate = LocalDate.of(2026, 5, 10)
        assertEquals(1, TimeUnit.DAY.between(secondDate, firstDate))
        assertEquals(1, TimeUnit.WEEK.between(secondDate, firstDate))
        assertEquals(1, TimeUnit.MONTH.between(secondDate, firstDate))
        assertEquals(1, TimeUnit.YEAR.between(secondDate, firstDate))
    }

    @Test
    fun `between returns 1 when current date is before the another date`() {
        val currentDate = LocalDate.of(2025, 5, 10)
        val secondDate = LocalDate.of(2026, 5, 10)
        assertEquals(1, TimeUnit.DAY.between(secondDate, currentDate))
        assertEquals(1, TimeUnit.WEEK.between(secondDate, currentDate))
        assertEquals(1, TimeUnit.MONTH.between(secondDate, currentDate))
        assertEquals(1, TimeUnit.YEAR.between(secondDate, currentDate))
    }
}
