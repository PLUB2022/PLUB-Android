package com.plub.domain.error

sealed class UiError:Throwable() {
    companion object{
        private const val UNAUTHORIZED_ERROR = 401
        private const val FORBIDDEN_ERROR = 403
        fun identifyHttpError(httpCode:Int, statusCode:Int) : UiError {
            return when(httpCode) {
                UNAUTHORIZED_ERROR -> UnauthorizedError.make(statusCode)
                FORBIDDEN_ERROR -> ForbiddenError.make(statusCode)
                else -> Invalided
            }
        }
    }

    object Invalided : UiError()
    object ServiceUnavailable : UiError()
}