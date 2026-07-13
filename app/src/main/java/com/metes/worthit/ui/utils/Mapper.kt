package com.metes.worthit.ui.utils

import com.metes.worthit.R
import com.metes.worthit.ui.entity.UiText
import okio.FileNotFoundException

fun Exception.toUiText(): UiText {
    return when(this) {
        is FileNotFoundException -> UiText.StringResource(R.string.file_not_found)
        is SecurityException -> UiText.StringResource(R.string.permission_denied)
        is IllegalArgumentException -> UiText.StringResource(R.string.form_is_incorrect)
        else -> UiText.StringResource(R.string.unknown_error)
    }
}