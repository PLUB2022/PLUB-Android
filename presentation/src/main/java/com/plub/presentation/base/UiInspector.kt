package com.plub.presentation.base

import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult

class UiInspector(private val delegate: Delegate) {

    interface Delegate {
        fun showLoading()
        fun hideLoading()
        fun handleCommonFailure(failure: CommonFailure)
        fun handleError(error: UiError)
    }

    fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: ((T) -> Unit)?, individualFailCallback: ((T, IndividualFailure) -> Unit)?) {
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
        delegate.showLoading()
    }

    private fun hideLoading() {
        delegate.hideLoading()
    }

    private fun<T> handleSuccess(data:T, result:StateResult, succeedCallback: ((T) -> Unit)?, individualFailCallback: ((T, IndividualFailure) -> Unit)?) {
        when(result) {
            is StateResult.Succeed -> succeedCallback?.invoke(data)
            is StateResult.Fail -> handleFailure(data,result,individualFailCallback)
        }
    }

    private fun<T> handleFailure(data:T, failure:StateResult.Fail, individualFailCallback: ((T, IndividualFailure) -> Unit)?) {
        when(failure) {
            is CommonFailure -> handleCommonFailure(failure)
            is IndividualFailure -> individualFailCallback?.invoke(data, failure)
        }
    }


    private fun handleCommonFailure(failure: CommonFailure) {
        delegate.handleCommonFailure(failure)
    }

    private fun handleError(uiError: UiError) {
        delegate.handleError(uiError)
    }
}