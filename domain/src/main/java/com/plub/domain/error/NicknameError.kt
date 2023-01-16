package com.plub.domain.error

sealed class NicknameError : IndividualError() {

    companion object {
        private const val DUPLICATED_NICKNAME = 2060
        fun make(statusCode: Int): NicknameError {
            return when (statusCode) {
                DUPLICATED_NICKNAME -> DuplicatedNickname("중복 닉네임")
                else -> Common
            }
        }
    }

    data class EmptyNickname(val msg: String) : NicknameError()
    data class DuplicatedNickname(val msg: String) : NicknameError()
    data class HasBlankNickname(val msg: String) : NicknameError()
    data class HasSpecialCharacter(val msg: String) : NicknameError()
    object Common : NicknameError()
}