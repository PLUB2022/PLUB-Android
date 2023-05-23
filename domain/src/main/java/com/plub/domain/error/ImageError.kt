package com.plub.domain.error

sealed class ImageError : IndividualError() {

    companion object {
        private const val FAIL_UPLOAD = 9050

        fun make(statusCode: Int): ImageError {
            return when (statusCode) {
                FAIL_UPLOAD -> FailUpload
                else -> Common
            }
        }
    }

    object FailUpload : ImageError()
    object Common : ImageError()
}