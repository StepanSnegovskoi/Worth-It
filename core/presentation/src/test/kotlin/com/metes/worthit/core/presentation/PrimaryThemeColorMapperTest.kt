package com.metes.worthit.core.presentation

import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor
import org.junit.Test
import kotlin.test.assertEquals

class PrimaryThemeColorMapperTest {

    @Test
    fun `each primary theme color entry has valid theme color`() {
        val expectedMappings = mapOf(
            PrimaryThemeColor.BLUE to ThemeColor.BLUE,
            PrimaryThemeColor.PINK to ThemeColor.PINK,
        )

        PrimaryThemeColor.entries.forEach { primaryThemeColor ->
            val expectedThemeColor = expectedMappings[primaryThemeColor]
            assertEquals(expectedThemeColor, primaryThemeColor.toThemeColor)
        }
    }

    @Test
    fun `each primary theme color entry has valid mapped name string resource`() {
        val expectedMappings = mapOf(
            PrimaryThemeColor.BLUE to R.string.blue,
            PrimaryThemeColor.PINK to R.string.pink,
        )

        PrimaryThemeColor.entries.forEach { primaryThemeColor ->
            val expectedRes = expectedMappings[primaryThemeColor]
            assertEquals(expectedRes, primaryThemeColor.nameStringRes)
        }
    }
}
