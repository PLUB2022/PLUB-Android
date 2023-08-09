package com.plub.domain.error

sealed class NicknameError : IndividualError() {

    companion object {
        private const val DUPLICATED_NICKNAME = 2010
        private const val LIMIT_CHANGE_NICKNAME = 2080
        private const val WRONG_VALUE = 2050

        fun make(statusCode: Int): NicknameError {
            return when (statusCode) {
                DUPLICATED_NICKNAME -> DuplicatedNickname("중복 닉네임")
                LIMIT_CHANGE_NICKNAME -> LimitChangeNickname("닉네임 변경 1회 초과")
                WRONG_VALUE -> HasSpecialCharacter("잘못된 값 입력")
                else -> Common
            }
        }
    }

    data class EmptyNickname(val msg: String) : NicknameError()
    data class DuplicatedNickname(val msg: String) : NicknameError()
    data class HasBlankNickname(val msg: String) : NicknameError()
    data class HasSpecialCharacter(val msg: String) : NicknameError()
    data class LimitChangeNickname(val msg: String) : NicknameError()
    object Common : NicknameError()
}