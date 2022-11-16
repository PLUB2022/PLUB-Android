package com.plub.domain.error

sealed class UnauthorizedError : UiError() {
    companion object {
        fun make(statusCode: Int): UnauthorizedError {
            return Common
        }
    }

    object Common : UnauthorizedError()
}