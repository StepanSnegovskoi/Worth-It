package com.metes.worthit.core.presentation

import com.metes.worthit.core.domain.entity.TimeUnit
import org.junit.Test
import kotlin.test.assertEquals

class TimeUnitMapperTest {

    @Test
    fun `each time unit entry has valid mapped name string resource `() {
        val expectedMappings = mapOf(
            TimeUnit.DAY to R.string.time_unit_day,
            TimeUnit.WEEK to R.string.time_unit_week,
            TimeUnit.MONTH to R.string.time_unit_month,
            TimeUnit.YEAR to R.string.time_unit_year,
        )

        TimeUnit.entries.forEach { timeUnit ->
            val expectedRes = expectedMappings[timeUnit]
            assertEquals(expectedRes, timeUnit.nameStringRes)
        }
    }

    @Test
    fun `each time unit entry has valid mapped price per plural string resource`() {
        val expectedMappings = mapOf(
            TimeUnit.DAY to R.plurals.price_per_day,
            TimeUnit.WEEK to R.plurals.price_per_week,
            TimeUnit.MONTH to R.plurals.price_per_month,
            TimeUnit.YEAR to R.plurals.price_per_year,
        )

        TimeUnit.entries.forEach { timeUnit ->
            val expectedRes = expectedMappings[timeUnit]
            assertEquals(expectedRes, timeUnit.pricePerPluralRes)
        }
    }

    @Test
    fun `each time unit entry has valid mapped name plural string resource`() {
        val expectedMappings = mapOf(
            TimeUnit.DAY to R.plurals.day,
            TimeUnit.WEEK to R.plurals.week,
            TimeUnit.MONTH to R.plurals.month,
            TimeUnit.YEAR to R.plurals.year,
        )

        TimeUnit.entries.forEach { timeUnit ->
            val expectedRes = expectedMappings[timeUnit]
            assertEquals(expectedRes, timeUnit.namePluralRes)
        }
    }
}
