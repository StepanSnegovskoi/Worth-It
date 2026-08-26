package com.metes.worthit.core.common

inline fun <reified R> Any.continueIfInstance(lambda: (R) -> Unit) {
    if (this is R) {
        lambda(this)
    }
}
