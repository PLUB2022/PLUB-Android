package com.plub.presentation.base

import android.content.Context
import com.plub.domain.UiState
import com.plub.domain.error.HttpError
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult

class UiInspector(val context:Context) {

    private val commonProcessor = CommonProcessor(context)

    fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: (T) -> Unit, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        when(uiState) {
            is UiState.Loading -> showLoading()
            is UiState.Success -> {
                handleSuccess(uiState.data,uiState.result,succeedCallback, individualFailCallback)
                hideLoading()
            }
            is UiState.Error -> {
                handleError(uiState.error)
                hideLoading()
            }
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun<T> handleSuccess(data:T, result:StateResult, succeedCallback: (T) -> Unit, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        when(result) {
            is StateResult.Succeed -> succeedCallback.invoke(data)
            is StateResult.Fail -> handleFailure(data,result,individualFailCallback)
        }
    }

    private fun<T> handleFailure(data:T, failure:StateResult.Fail, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        when(failure) {
            is CommonFailure -> handleCommonFailure(failure)
            is IndividualFailure -> individualFailCallback?.invoke(data, failure)
        }
    }


    private fun handleCommonFailure(failure: CommonFailure) {
        commonProcessor.failProcess(failure)
    }

    private fun handleError(httpError: HttpError) {
        commonProcessor.errorProcess(httpError)
    }
}