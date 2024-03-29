package com.plub.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.CommonError
import com.plub.domain.error.IndividualError
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.EventFlow
import com.plub.presentation.util.MutableEventFlow
import com.plub.presentation.util.UiInspector
import com.plub.presentation.util.asEventFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseTestViewModel<STATE: PageState> : ViewModel() {

    private val uiInspector = UiInspector(delegate())

    abstract val uiState:STATE

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event> = _eventFlow.asEventFlow()

    private val _showProgress = MutableEventFlow<Boolean>()
    val showProgress: EventFlow<Boolean> = _showProgress.asEventFlow()

    private val _commonError = MutableSharedFlow<CommonError>(0, 1, BufferOverflow.DROP_OLDEST)
    val commonError: SharedFlow<CommonError> = _commonError.asSharedFlow()

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: ((T) -> Unit), individualErrorCallback: ((T?, IndividualError) -> Unit)? = null, needShowLoading: Boolean = true) {
        uiInspector.inspectUiState(uiState,succeedCallback, individualErrorCallback, needShowLoading)
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