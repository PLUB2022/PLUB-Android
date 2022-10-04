package com.plub.presentation.ui.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin
import com.plub.domain.successOrNull
import com.plub.domain.usecase.TrySampleLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<SampleLogin>>(UiState.Loading)
    val uiState: StateFlow<UiState<SampleLogin>> = _uiState.asStateFlow()

    init {
        trySampleLogin()
    }

    fun trySampleLogin() = viewModelScope.launch {
        trySampleLoginUseCase.invoke().collect {
            _uiState.value = it
        }
    }

    fun showErrorPage() {
        _uiState.value = UiState.Error(null)
    }

    val loginText: StateFlow<String?> = _uiState.mapLatest { state ->
        state.successOrNull()?.login ?: ""
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = "loading"
    )
}