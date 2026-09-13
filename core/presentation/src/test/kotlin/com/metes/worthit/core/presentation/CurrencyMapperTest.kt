package com.metes.worthit.core.presentation

import com.metes.worthit.core.domain.entity.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyMapperTest {

    @Test
    fun `each currency entry has valid mapped drawable resource`() {
        val expectedMappings = mapOf(
            Currency.EUR to R.drawable.euro_24dp,
            Currency.USD to R.drawable.usd_24dp,
            Currency.GBP to R.drawable.gbp_24dp,
            Currency.JPY to R.drawable.jpy_24dp,
            Currency.INR to R.drawable.inr_24dp,
            Currency.CNY to R.drawable.cny_24dp,
        )

        assertEquals(
            expectedMappings.size,
            Currency.entries.size
        )

        Currency.entries.forEach { currency ->
            val expectedRes = expectedMappings[currency]
            assertEquals(expectedRes, currency.iconResId)
        }
    }
}
