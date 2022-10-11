package com.plub.domain.result

sealed class CommonFailure: StateResult.Fail() {

    companion object{
        private const val TOKEN_INVALIDED_FAILURE = 203
        fun make(code:Int) : CommonFailure{
            return when(code) {
                TOKEN_INVALIDED_FAILURE -> TokenInvalided("토큰 오류")
                else -> Invalided
            }
        }
    }

    object Invalided : CommonFailure()
    data class TokenInvalided(val msg:String):CommonFailure()
}
