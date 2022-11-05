package com.plub.domain.error

sealed class HttpError:Throwable() {
    companion object{
        private const val UNAUTHORIZED_ERROR = 401
        private const val FORBIDDEN_ERROR = 403
        fun identifyError(httpCode:Int,statusCode:Int) : HttpError {
            return when(httpCode) {
                UNAUTHORIZED_ERROR -> UnauthorizedError.make(statusCode)
                FORBIDDEN_ERROR -> ForbiddenError.make(statusCode)
                else -> Invalided
            }
        }
    }

    object Invalided : HttpError()
    object ServiceUnavailable : HttpError()
}