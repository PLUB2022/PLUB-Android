package com.plub.domain.result

sealed class Failure: StateResult.Fail() {
    companion object{
        private val COMMON_FAILURE = 400..450

        fun identifyFailure(statusCode:Int): Failure {
            return when(statusCode) {
                in COMMON_FAILURE -> CommonFailure.make(statusCode)
                else -> IndividualFailure.Undefined
            }
        }
    }
}