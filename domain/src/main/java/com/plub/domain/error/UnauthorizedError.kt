package com.plub.domain.error

sealed class UnauthorizedError : HttpError() {
    companion object {
        fun make(statusCode: Int): UnauthorizedError {
            return Common
        }
    }

    object Common : UnauthorizedError()
}