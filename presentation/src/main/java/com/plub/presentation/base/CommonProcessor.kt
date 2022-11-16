package com.plub.presentation.base

import android.content.Context
import com.plub.domain.error.UiError
import com.plub.domain.result.CommonFailure

class CommonProcessor(val context: Context) {
    fun errorProcess(uiError: UiError) {
        //TODO 다이얼로그 표시 or 동작
    }

    fun failProcess(commonFailure: CommonFailure) {
        //TODO 다이얼로그 표시 or 동작
    }
}