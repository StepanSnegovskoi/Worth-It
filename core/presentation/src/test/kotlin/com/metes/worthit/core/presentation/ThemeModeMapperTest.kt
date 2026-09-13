package com.metes.worthit.core.presentation

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.domain.entity.ThemeMode
import org.junit.Test
import kotlin.test.assertEquals

class ThemeModeMapperTest {

    @Test
    fun `each theme mode entry has valid mapped name string resource `() {
        val expectedMappings = mapOf(
            ThemeMode.DARK to R.string.dark,
            ThemeMode.LIGHT to R.string.light,
            ThemeMode.SYSTEM to R.string.system,
        )

        ThemeMode.entries.forEach { themeMode ->
            val expectedRes = expectedMappings[themeMode]
            assertEquals(expectedRes, themeMode.nameStringRes)
        }
    }

    @Test
    fun `each theme mode entry has valid Brush`() {
        val expectedMappings = mapOf(
            ThemeMode.DARK to Brush.linearGradient(listOf(Color.Black, Color.Black)),
            ThemeMode.LIGHT to Brush.linearGradient(listOf(Color.White, Color.White)),
            ThemeMode.SYSTEM to Brush.linearGradient(
                0.0f to Color.White,
                0.5f to Color.White,
                0.5f to Color.Black,
                1f to Color.Black,
            ),
        )

        ThemeMode.entries.forEach { themeMode ->
            val expectedBrush = expectedMappings[themeMode]
            assertEquals(expectedBrush, themeMode.linearBrushGradient)
        }
    }
}
