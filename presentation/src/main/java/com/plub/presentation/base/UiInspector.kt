package com.plub.presentation.base

import com.plub.domain.UiState
import com.plub.domain.error.CommonError
import com.plub.domain.error.IndividualError
import com.plub.domain.error.UiError

class UiInspector(private val delegate: Delegate) {

    interface Delegate {
        fun showLoading()
        fun hideLoading()
        fun handleCommonError(commonError: CommonError)
    }

    fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: (T) -> Unit, individualErrorCallback: ((T?, IndividualError) -> Unit)?) {
        when(uiState) {
            is UiState.Loading -> showLoading()
            is UiState.Success -> {
                succeedCallback.invoke(uiState.data)
                hideLoading()
            }
            is UiState.Error -> {
                handleError(uiState.data, uiState.error, individualErrorCallback)
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

    private fun<T> handleError(data:T?, error:UiError, individualErrorCallback: ((T?, IndividualError) -> Unit)?) {
        when(error) {
            is CommonError -> delegate.handleCommonError(error)
            is IndividualError -> individualErrorCallback?.invoke(data, error)
        }
    }
}