package com.plub.domain.error

sealed class SearchError: IndividualError() {
    companion object {
        private const val NOT_FOUND_RECRUIT = 6050

        fun make(statusCode: Int): SearchError {
            return when(statusCode) {
                NOT_FOUND_RECRUIT -> NotFoundRecruit("모임을 찾을 수 없음")
                else -> Common
            }
        }
    }
    data class NotFoundRecruit(val msg:String): SearchError()
    object Common: SearchError()
}