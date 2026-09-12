package com.metes.worthit.core.domain.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ResultExtTest {

    private val resultSuccess = Result.Success<Int>(5)
    private val resultError = Result.Error<Exception>(IllegalArgumentException("Test exception"))

    @Test
    fun `onSuccess should invoke action when result is Success`() {
        var isSuccessCalled = false

        resultSuccess.onSuccess {
            isSuccessCalled = true
        }
        resultError.onSuccess {
            isSuccessCalled = false
        }

        assertTrue(isSuccessCalled)
    }

    @Test
    fun `onError should invoke action when result is Error`() {
        var isErrorCalled = false

        resultError.onError {
            isErrorCalled = true
        }
        resultSuccess.onError {
            isErrorCalled = false
        }

        assertTrue(isErrorCalled)
    }

    @Test
    fun `onResult should invoke onSuccess on Success and onFailure on Error`() {
        var isSuccessInvoked = false

        resultSuccess.onResult(
            onSuccess = { isSuccessInvoked = true },
            onError = { isSuccessInvoked = false },
        )
        assertEquals(isSuccessInvoked, true)
    }

    @Test
    fun `onResult called onError if result is Error`() {
        var isErrorInvoked = false

        resultError.onResult(
            onSuccess = { isErrorInvoked = false },
            onError = { isErrorInvoked = true },
        )
        assertEquals(isErrorInvoked, true)
    }
}
