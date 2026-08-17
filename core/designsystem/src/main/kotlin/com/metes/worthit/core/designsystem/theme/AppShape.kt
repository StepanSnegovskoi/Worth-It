package com.metes.worthit.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class AppShape(
    val container: Shape = RoundedCornerShape(16.dp),
    val button: Shape = RoundedCornerShape(8.dp),
)

val LocalAppShape = staticCompositionLocalOf {
    AppShape()
}
