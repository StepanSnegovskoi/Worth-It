package com.metes.worthit.core.designsystem.extensions

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

fun Modifier.clickableIfNotNull(
    onClick: (() -> Unit)? = null,
    role: Role? = null,
): Modifier {
    return if (onClick != null) {
        this.clickable(role = role, onClick = onClick)
    } else {
        this
    }
}
