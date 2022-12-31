package com.plub.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.presentation.state.PageState
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState>(
    initialState: STATE
) : ViewModel() {

    private val uiInspector = UiInspector(delegate())

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    private val _showProgress = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val showProgress: SharedFlow<Boolean> = _showProgress.asSharedFlow()

    private val _commonFailure = MutableSharedFlow<CommonFailure>(0, 1, BufferOverflow.DROP_OLDEST)
    val commonFailure: SharedFlow<CommonFailure> = _commonFailure.asSharedFlow()

    private val _uiError = MutableSharedFlow<UiError>(0, 1, BufferOverflow.DROP_OLDEST)
    val uiError: SharedFlow<UiError> = _uiError.asSharedFlow()

    protected fun updateUiState(update:(STATE) -> STATE) {
        viewModelScope.launch {
            _uiState.update { update.invoke(it) }
        }
    }

    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: ((T) -> Unit)? = null, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        uiInspector.inspectUiState(uiState,succeedCallback, individualFailCallback)
    }

    private fun delegate():UiInspector.Delegate {
        return object : UiInspector.Delegate {
            override fun showLoading() {
                viewModelScope.launch {
                    _showProgress.emit(true)
                }
            }

            override fun hideLoading() {
                viewModelScope.launch {
                    _showProgress.emit(false)
                }
            }

            override fun handleCommonFailure(failure: CommonFailure) {
                viewModelScope.launch {
                    _commonFailure.emit(failure)
                }
            }

            override fun handleError(error: UiError) {
                viewModelScope.launch {
                    _uiError.emit(error)
                }
            }
        }
    }
}