package com.plub.domain.result

sealed class NicknameFailure: IndividualFailure() {

    companion object {
        private const val DUPLICATED_NICKNAME = 1100
        fun make(statusCode: Int): NicknameFailure {
            return when(statusCode) {
                DUPLICATED_NICKNAME -> DuplicatedNickname("중복 닉네임")
                else -> Common
            }
        }
    }

    data class EmptyNickname(val msg:String): NicknameFailure()
    data class DuplicatedNickname(val msg:String): NicknameFailure()
    data class HasBlankNickname(val msg:String): NicknameFailure()
    data class HasSpecialCharacter(val msg:String): NicknameFailure()
    object Common: NicknameFailure()
}