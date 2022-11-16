package com.plub.domain.result


sealed class CommonFailure: Failure() {

    companion object{
        private const val DUPLICATE_LOGIN_DETECTED_FAILURE = 1001
        fun make(code:Int) : CommonFailure {
            return when(code) {
                DUPLICATE_LOGIN_DETECTED_FAILURE -> DuplicateLoginDetected("중복 로그인 오류")
                else -> Invalided
            }
        }
    }

    object Invalided : CommonFailure()
    data class DuplicateLoginDetected(val msg:String): CommonFailure()
}
