package com.plub.domain.error

sealed class UiError:Throwable() {
    companion object{
        private val COMMON_FAILURE = 500..600

        fun identifyError(statusCode:Int): UiError {
            return when(statusCode) {
                in COMMON_FAILURE -> CommonError.make(statusCode)
                else -> IndividualError.Undefined
            }
        }
    }
}