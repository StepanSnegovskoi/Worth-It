package com.metes.worthit.core.domain.utils

sealed interface Result<out D, out E> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E>(val data: E): Result<Nothing, E>
}
