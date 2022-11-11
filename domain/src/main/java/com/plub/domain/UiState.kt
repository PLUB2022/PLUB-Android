package com.plub.domain

import com.plub.domain.error.UiError
import com.plub.domain.result.StateResult


sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T, var result: StateResult): UiState<T>()
    data class Error(val error: UiError): UiState<Nothing>()
}

fun <T> UiState<T>.successOrNull(): T? = if (this is UiState.Success<T>) {
    data
} else {
    null
}

fun <T> UiState<T>.throwableOrNull(): Throwable? = if (this is UiState.Error) {
    error
} else {
    null
}