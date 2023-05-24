package com.plub.domain.error

sealed class TokenError: IndividualError() {
    companion object {
        private const val NOT_FOUND_REFRESH_TOKEN = 2040
        private const val EXPIRED_REFRESH_TOKEN = 2130
        private const val NOT_REFRESH_TOKEN = 2120

        fun make(statusCode: Int): TokenError {
            return when(statusCode) {
                NOT_FOUND_REFRESH_TOKEN -> NotFoundRefreshToken("Refresh Token값 없음")
                EXPIRED_REFRESH_TOKEN -> ExpiredRefreshToken("Refresh Token값 만료")
                NOT_REFRESH_TOKEN -> NotRefreshToken("Refresh Token이 아님")
                else -> Common
            }
        }
    }
    data class NotFoundRefreshToken(val msg:String): TokenError()
    data class ExpiredRefreshToken(val msg:String): TokenError()
    data class NotRefreshToken(val msg:String): TokenError()
    object Common: TokenError()
}