package com.metes.worthit.app

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> T?.throwIfNull(e: Throwable): T {
    contract {
        returns() implies (this@throwIfNull != null)
    }
    if (this == null) throw e
    return this
}
