package com.plub.domain.error

sealed class ForbiddenError: HttpError() {
    companion object{

        private const val STATUS_BLOCKED_USER = 400
        private const val STATUS_DUPLICATED_USER = 401
        private const val STATUS_DORMANCY_USER = 402
        fun make(statusCode: Int) : ForbiddenError {
            return when(statusCode) {
                STATUS_BLOCKED_USER -> BlockedUserAccess("정지유저")
                STATUS_DUPLICATED_USER -> DuplicatedUserAccess("중복유저")
                STATUS_DORMANCY_USER -> DormancyUserAccess("휴면유저")
                else -> Common
            }
        }
    }

    object Common:ForbiddenError()
    data class BlockedUserAccess(val msg:String): ForbiddenError()
    data class DuplicatedUserAccess(val msg:String): ForbiddenError()
    data class DormancyUserAccess(val msg: String): ForbiddenError()
}