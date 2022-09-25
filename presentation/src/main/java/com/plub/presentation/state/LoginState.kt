package com.plub.presentation.state

sealed class LoginState{
    object Loading : LoginState()
    object Success : LoginState()
    data class Fail(val message : String?) : LoginState()
}
