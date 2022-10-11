package com.plub.domain.result

sealed class StateResult {
    companion object {
        const val SUCCEED_CODE = 200
        private val COMMON_FAILURE = 400..450

        fun identifyFailure(customCode:Int): Fail {
            return when(customCode) {
                in COMMON_FAILURE -> CommonFailure.make(customCode)
                else -> IndividualFailure.Undefined
            }
        }
    }

    object Succeed: StateResult()
    sealed class Fail: StateResult()
}
