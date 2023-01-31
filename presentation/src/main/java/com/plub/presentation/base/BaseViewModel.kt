package com.plub.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.CommonError
import com.plub.domain.error.IndividualError
import com.plub.presentation.event.Event
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.UiInspector
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState>(
    initialState: STATE
) : ViewModel() {

    private val uiInspector = UiInspector(delegate())

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Event>(0, 1, BufferOverflow.DROP_OLDEST)
    val eventFlow: SharedFlow<Event> = _eventFlow.asSharedFlow()

    private val _showProgress = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)
    val showProgress: SharedFlow<Boolean> = _showProgress.asSharedFlow()

    private val _commonError = MutableSharedFlow<CommonError>(0, 1, BufferOverflow.DROP_OLDEST)
    val commonError: SharedFlow<CommonError> = _commonError.asSharedFlow()

    protected fun updateUiState(update:(STATE) -> STATE) {
        viewModelScope.launch {
            _uiState.update { update.invoke(it) }
        }
    }

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: ((T) -> Unit), individualErrorCallback: ((T?, IndividualError) -> Unit)? = null) {
        uiInspector.inspectUiState(uiState,succeedCallback, individualErrorCallback)
    }

    private fun delegate(): UiInspector.Delegate {
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

            override fun handleCommonError(commonError: CommonError) {
                viewModelScope.launch {
                    _commonError.emit(commonError)
                }
            }
        }
    }
}