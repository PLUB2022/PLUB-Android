package com.plub.domain.error

sealed class SignUpError : IndividualError() {

    companion object {
        private const val DUPLICATED_EMAIL = 2070
        private const val DUPLICATED_NICKNAME = 2060
        private const val NOT_FOUND_CATEGORY = 3000

        fun make(statusCode: Int): SignUpError {
            return when (statusCode) {
                DUPLICATED_EMAIL -> DuplicatedEmail("중복 닉네임")
                DUPLICATED_NICKNAME -> DuplicatedNickname("중복 닉네임")
                NOT_FOUND_CATEGORY -> NotFoundCategory("중복 닉네임")
                else -> Common
            }
        }
    }

    data class DuplicatedEmail(val msg: String) : SignUpError()
    data class DuplicatedNickname(val msg: String) : SignUpError()
    data class NotFoundCategory(val msg: String) : SignUpError()
    object Common : SignUpError()
}