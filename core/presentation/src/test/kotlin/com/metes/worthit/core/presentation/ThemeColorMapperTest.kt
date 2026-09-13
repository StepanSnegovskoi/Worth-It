package com.metes.worthit.core.presentation

import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor
import org.junit.Test
import kotlin.test.assertEquals

class ThemeColorMapperTest {

    @Test
    fun `each theme mode entry has valid primary theme color`() {
        val expectedMappings = mapOf(
            ThemeColor.BLUE to PrimaryThemeColor.BLUE,
            ThemeColor.PINK to PrimaryThemeColor.PINK,
        )

        ThemeColor.entries.forEach { themeColor ->
            val expectedPrimaryThemeColor = expectedMappings[themeColor]
            assertEquals(expectedPrimaryThemeColor, themeColor.toPrimaryThemeColor())
        }
    }

    @Test
    fun `each theme mode entry has valid mapped name string resource`() {
        val expectedMappings = mapOf(
            ThemeColor.BLUE to R.string.blue,
            ThemeColor.PINK to R.string.pink,
        )


        ThemeColor.entries.forEach { themeColor ->
            val expectedRes = expectedMappings[themeColor]
            assertEquals(expectedRes, themeColor.nameStringRes)
        }
    }
}
