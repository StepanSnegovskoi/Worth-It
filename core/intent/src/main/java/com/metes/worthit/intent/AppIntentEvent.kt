package com.metes.worthit.intent

sealed interface AppIntentEvent {
    data class Image(val imageUri: String) : AppIntentEvent
    data object Ignored : AppIntentEvent
}