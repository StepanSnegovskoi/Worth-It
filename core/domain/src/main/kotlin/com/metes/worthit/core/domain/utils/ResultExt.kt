package com.metes.worthit.core.domain.utils

inline fun <S, E> Result<S, E>.onError(onError: (E) -> Unit): Result<S, E> {
    if (this is Result.Error) {
        onError(this.data)
    }
    return this
}

inline fun <S, E> Result<S, E>.onSuccess(onSuccess: (S) -> Unit): Result<S, E> {
    if (this is Result.Success) {
        onSuccess(this.data)
    }
    return this
}
