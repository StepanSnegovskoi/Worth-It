package com.metes.worthit.core.domain.entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

class ThemeColorTest {

    @Test
    fun `fromNameOrDefault returns default value BLUE for invalid theme color names`() {
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault(Uuid.random().toString()))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("dggggg"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("gfffff"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("aaaaa"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("        ||||||"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("||||||"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("     ||||||     "))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("||||||        "))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("hello world"))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault(""))
        assertEquals(ThemeColor.BLUE, ThemeColor.fromNameOrDefault("           "))
    }

    @Test
    fun `fromNameOrDefault returns correct theme color for correct theme color name`() {
        ThemeColor.entries.forEach { themeColor ->
            assertEquals(themeColor, ThemeColor.fromNameOrDefault(themeColor.name))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme color when theme color name converted to upper case`() {
        ThemeColor.entries.forEach { themeColor ->
            assertEquals(themeColor, ThemeColor.fromNameOrDefault(themeColor.name.uppercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme color when theme color name converted to lower case`() {
        ThemeColor.entries.forEach { themeColor ->
            assertEquals(themeColor, ThemeColor.fromNameOrDefault(themeColor.name.lowercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme color when theme color name has mixed case`() {
        ThemeColor.entries.forEach { themeColor ->
            val mixedName = buildString {
                themeColor.name.forEachIndexed { index, char ->
                    append(if (index % 2 == 0) char.uppercase() else char.lowercase())
                }
            }
            assertEquals(themeColor, ThemeColor.fromNameOrDefault(mixedName))
        }
    }
}
