package com.metes.worthit.core.domain.entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

class ThemeModeTest {

    @Test
    fun `fromNameOrDefault returns default value SYSTEM for invalid theme mode names`() {
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault(Uuid.random().toString()))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("dggggg"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("gfffff"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("aaaaa"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("        ||||||"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("||||||"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("     ||||||     "))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("||||||        "))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("hello world"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault(""))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromNameOrDefault("           "))
    }

    @Test
    fun `fromNameOrDefault returns correct theme mode for correct theme mode name`() {
        ThemeMode.entries.forEach { themeMode ->
            assertEquals(themeMode, ThemeMode.fromNameOrDefault(themeMode.name))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme mode when theme mode name converted to upper case`() {
        ThemeMode.entries.forEach { themeMode ->
            assertEquals(themeMode, ThemeMode.fromNameOrDefault(themeMode.name.uppercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme mode when theme mode name converted to lower case`() {
        ThemeMode.entries.forEach { themeMode ->
            assertEquals(themeMode, ThemeMode.fromNameOrDefault(themeMode.name.lowercase()))
        }
    }

    @Test
    fun `fromNameOrDefault returns correct theme mode when theme mode name has mixed case`() {
        ThemeMode.entries.forEach { themeMode ->
            val mixedName = buildString {
                themeMode.name.forEachIndexed { index, char ->
                    append(if (index % 2 == 0) char.uppercase() else char.lowercase())
                }
            }
            assertEquals(themeMode, ThemeMode.fromNameOrDefault(mixedName))
        }
    }
}
