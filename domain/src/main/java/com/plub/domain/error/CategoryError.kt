package com.plub.domain.error

sealed class CategoryError: IndividualError() {

    companion object{
        private const val NOT_FOUND_CATEGORY = 3000

        fun make(code:Int) : CategoryError {
            return when(code) {
                NOT_FOUND_CATEGORY -> NotFoundCategory("카테고리가 디비에 없음")
                else -> Common
            }
        }
    }

    data class NotFoundCategory(val msg:String): CategoryError()
    object Common : CategoryError()
}
