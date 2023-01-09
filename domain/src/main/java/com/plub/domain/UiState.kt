package com.plub.domain

import com.plub.domain.error.UiError


sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error<T>(val data: T?, var error: UiError): UiState<T>()
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