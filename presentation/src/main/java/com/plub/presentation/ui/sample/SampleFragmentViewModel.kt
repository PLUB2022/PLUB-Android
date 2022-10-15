package com.plub.presentation.ui.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.PlubJwtTokenRepository
import com.plub.domain.successOrNull
import com.plub.domain.usecase.TrySampleLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase,
    private val plubJwtTokenRepository: PlubJwtTokenRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<SampleLogin>>(UiState.Loading)
    val uiState: StateFlow<UiState<SampleLogin>> = _uiState.asStateFlow()

    val acTokenInput = MutableStateFlow("")
    val reTokenInput = MutableStateFlow("")

    private val _acToken = MutableStateFlow("")
    val acToken: StateFlow<String> = _acToken.asStateFlow()

    private val _reToken = MutableStateFlow("")
    val reToken: StateFlow<String> = _reToken.asStateFlow()

    init {
        trySampleLogin()
    }

    fun saveAcToken() = viewModelScope.launch {
        plubJwtTokenRepository.saveAccessToken(acTokenInput.value)
    }

    fun saveReToken() = viewModelScope.launch {
        plubJwtTokenRepository.saveAccessTokenAndRefreshToken(acTokenInput.value, reTokenInput.value)
    }

    fun getAccessToken() = viewModelScope.launch {
        _acToken.value = plubJwtTokenRepository.getAccessToken()
    }

    fun getRefreshToken() = viewModelScope.launch {
        _reToken.value = plubJwtTokenRepository.getRefreshToken()
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