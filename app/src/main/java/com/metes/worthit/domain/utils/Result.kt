package com.metes.worthit.domain.utils

sealed interface Result<out D, out E> {
    data class Success<out D>(val item: D): Result<D, Nothing>
    data class Error<out E>(val error: E): Result<Nothing, E>
}