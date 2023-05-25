package com.plub.domain.error

sealed class FeedError: IndividualError() {

    companion object{
        private const val NOT_FOUND_FEED = 8000
        private const val NOT_FOUND_COMMENT = 8010
        private const val NOT_FEED_AUTHOR = 8020
        private const val DELETED_FEED = 8030
        private const val CANNOT_DELETE_SYSTEM_FEED = 8040
        private const val DELETED_COMMENT = 8050
        private const val MAX_FEED_PIN = 8060

        fun make(code:Int) : FeedError {
            return when(code) {
                NOT_FOUND_FEED -> NotFoundFeed("게시글 id를 찾을 수 없음")
                NOT_FOUND_COMMENT -> NotFoundComment("댓글 id를 찾을 수 없음")
                NOT_FEED_AUTHOR -> NotFeedAuthor("권한이 없음")
                DELETED_FEED -> DeletedFeed("이미 삭제된 게시글")
                CANNOT_DELETE_SYSTEM_FEED -> CannotDeleteSystemFeed("시스템 피드를 삭제할 수 없음")
                DELETED_COMMENT -> DeletedComment("이미 삭제된 댓글")
                MAX_FEED_PIN -> MaxFeedPin("최대 고정 갯수 초과")
                else -> Common
            }
        }
    }

    data class NotFoundFeed(val msg:String): FeedError()
    data class NotFoundComment(val msg:String): FeedError()
    data class NotFeedAuthor(val msg:String): FeedError()
    data class DeletedFeed(val msg:String): FeedError()
    data class CannotDeleteSystemFeed(val msg:String): FeedError()
    data class DeletedComment(val msg:String): FeedError()
    data class MaxFeedPin(val msg:String): FeedError()
    object Common : FeedError()
}
