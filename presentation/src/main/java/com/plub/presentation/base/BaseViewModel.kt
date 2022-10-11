package com.plub.presentation.base

import androidx.lifecycle.ViewModel
import com.plub.domain.UiState
import com.plub.domain.model.state.PageState
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<STATE:PageState>(
    initialState: STATE
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    protected fun updateUiState(update:(STATE) -> STATE) {
        _uiState.update { update.invoke(it) }
    }
}