package com.plub.domain.error

sealed class ImageError : IndividualError() {

    companion object {
        private const val FAIL_UPLOAD = 9040
        private const val FAIL_DELETE = 9050

        fun make(statusCode: Int): ImageError {
            return when (statusCode) {
                FAIL_UPLOAD -> FailUpload("이미지 업로드에 실패")
                FAIL_DELETE -> FailDelete("이미지 삭제 실패")
                else -> Common
            }
        }
    }

    data class FailUpload(val msg : String) : ImageError()
    data class FailDelete(val msg : String) : ImageError()
    object Common : ImageError()
}