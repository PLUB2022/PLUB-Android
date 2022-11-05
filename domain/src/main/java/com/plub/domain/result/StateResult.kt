package com.plub.domain.result

sealed class StateResult {
    companion object {
        const val SUCCEED_CODE = 200
    }

    object Succeed: StateResult()
    sealed class Fail: StateResult()
}
