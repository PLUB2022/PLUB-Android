package com.plub.domain.error

sealed class CommonError: UiError() {

    companion object{
        private const val SERVER_DIALOG_ERROR = 1001
        fun make(code:Int) : CommonError {
            return when(code) {
                SERVER_DIALOG_ERROR -> ServerDialogError("다이얼로그 표시")
                else -> ServiceUnavailable
            }
        }
    }

    object ServiceUnavailable : CommonError()
    data class ServerDialogError(val msg:String): CommonError()
}
