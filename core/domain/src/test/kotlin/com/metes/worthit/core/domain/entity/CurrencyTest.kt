package com.metes.worthit.core.domain.entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

class CurrencyTest {

    @Test
    fun `fromNameOrDefault returns default value EUR for invalid currency names`() {
        assertEquals(Currency.EUR, Currency.fromNameOrDefault(Uuid.random().toString()))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("dggggg"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("gfffff"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("aaaaa"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("        ||||||"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("||||||"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("     ||||||     "))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("||||||        "))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("hello world"))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault(""))
        assertEquals(Currency.EUR, Currency.fromNameOrDefault("           "))
    }

    @Test
    fun `fromNameOrDefault returns correct currency for correct currency name`() {
        Currency.entries.forEach { currency ->
            assertEquals(currency, Currency.fromNameOrDefault(currency.name))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct currency when currency name converted to upper case`() {
        Currency.entries.forEach { currency ->
            assertEquals(currency, Currency.fromNameOrDefault(currency.name.uppercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct currency when currency name converted to lower case`() {
        Currency.entries.forEach { currency ->
            assertEquals(currency, Currency.fromNameOrDefault(currency.name.lowercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct currency when currency name has mixed case`() {
        Currency.entries.forEach { currency ->
            val mixedName = buildString {
                currency.name.forEachIndexed { index, char ->
                    append(if (index % 2 == 0) char.uppercase() else char.lowercase())
                }
            }
            assertEquals(currency, Currency.fromNameOrDefault(mixedName))
        }
    }
}
