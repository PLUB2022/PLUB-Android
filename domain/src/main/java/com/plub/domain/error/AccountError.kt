package com.plub.domain.error

sealed class AccountError : IndividualError() {

    companion object {
        private const val NOT_FOUND_ACCOUNT = 2000
        private const val DUPLICATED_NICKNAME = 2010
        private const val DUPLICATED_EMAIL = 2020
        private const val INVALID_SOCIAL_TYPE = 2030
        private const val ROLE_ACCESS = 2040
        private const val INVALID_NICKNAME = 2050
        private const val SELF_REPORT = 2060
        private const val SUSPENDED_ACCOUNT = 2070
        private const val NICKNAME_CHANGE_LIMIT = 2080
        private const val ALREADY_INACTIVE_ACCOUNT = 2090

        fun make(statusCode: Int): AccountError {
            return when (statusCode) {
                NOT_FOUND_ACCOUNT -> NotFoundAccount("디비에 유저가 없음")
                DUPLICATED_NICKNAME -> DuplicatedNickname("중복된 닉네임")
                DUPLICATED_EMAIL -> DuplicatedEmail("중복된 이메일")
                INVALID_SOCIAL_TYPE -> InvalidSocialType("소셜 타입 에러")
                ROLE_ACCESS -> RoleAccess("어드민 로그인 접근 권한 없음")
                INVALID_NICKNAME -> InvalidNickname("닉네임 생성 규칙 위반")
                SELF_REPORT -> SelfReport("자가 신고 불가능")
                SUSPENDED_ACCOUNT -> SuspendedAccount("정지된 계정")
                NICKNAME_CHANGE_LIMIT -> NicknameChangeLimit("닉네임 변경 횟수 제한")
                ALREADY_INACTIVE_ACCOUNT -> AlreadyInactiveAccount("이미 비활성화 된 계정")
                else -> Common
            }
        }
    }

    data class NotFoundAccount(val msg: String) : AccountError()
    data class DuplicatedNickname(val msg: String) : AccountError()
    data class DuplicatedEmail(val msg: String) : AccountError()
    data class InvalidSocialType(val msg: String) : AccountError()
    data class RoleAccess(val msg: String) : AccountError()
    data class InvalidNickname(val msg: String) : AccountError()
    data class SelfReport(val msg: String) : AccountError()
    data class SuspendedAccount(val msg: String) : AccountError()
    data class NicknameChangeLimit(val msg: String) : AccountError()
    data class AlreadyInactiveAccount(val msg: String) : AccountError()
    object Common : AccountError()
}