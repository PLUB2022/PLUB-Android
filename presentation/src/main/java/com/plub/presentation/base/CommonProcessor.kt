package com.plub.presentation.base

import android.content.Context
import com.plub.domain.UiState
import com.plub.domain.error.HttpError
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult

class CommonProcessor(val context: Context) {
    fun errorProcess(httpError: HttpError) {
        //TODO 다이얼로그 표시 or 동작
    }

    fun failProcess(commonFailure: CommonFailure) {
        //TODO 다이얼로그 표시 or 동작
    }
}