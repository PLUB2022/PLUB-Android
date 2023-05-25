package com.plub.domain.error

sealed class TodoError: IndividualError() {

    companion object{
        private const val NOT_FOUND_TODO = 7000
        private const val NOT_COMPLETE_TODO = 7010
        private const val ALREADY_CHECKED_TODO = 7020
        private const val ALREADY_PROOF_TODO = 7030
        private const val NOT_FOUND_TIMELINE = 7040
        private const val TOO_MANY_TODO = 7050

        fun make(code:Int) : TodoError {
            return when(code) {
                NOT_FOUND_TODO -> NotFoundTodo("Todo를 찾을 수 없음")
                NOT_COMPLETE_TODO -> NotCompleteTodo("투두가 완료되지 않음")
                ALREADY_CHECKED_TODO -> AlreadyCheckedTodo("중복 todo check")
                ALREADY_PROOF_TODO -> AlreadyProofTodo("중복 proof")
                NOT_FOUND_TIMELINE -> NotFoundTimeline("해당 날짜에 투두 없음")
                TOO_MANY_TODO -> TooManyTodo("투두 갯수 초과")
                else -> Common
            }
        }
    }

    data class NotFoundTodo(val msg:String): TodoError()
    data class NotCompleteTodo(val msg:String): TodoError()
    data class AlreadyCheckedTodo(val msg:String): TodoError()
    data class AlreadyProofTodo(val msg:String): TodoError()
    data class NotFoundTimeline(val msg:String): TodoError()
    data class TooManyTodo(val msg:String): TodoError()
    object Common : TodoError()
}
