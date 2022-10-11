package com.plub.domain.result

sealed class LoginFailure: IndividualFailure() {
    companion object {
        const val INVALIDED_ACCOUNT_CODE = 456
    }
    data class InvalidedAccount(val msg:String):LoginFailure()
}