package com.plub.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.HttpError
import com.plub.domain.error.UnauthorizedError
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.PlubJwtTokenRepository
import com.plub.domain.successOrNull
import com.plub.domain.model.state.SampleLoginPageState
import com.plub.domain.usecase.TrySampleLoginUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase,
    private val plubJwtTokenRepository: PlubJwtTokenRepository
) : BaseViewModel<SampleLoginPageState>(SampleLoginPageState()) {

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
        trySampleLoginUseCase.invoke(Unit).collect { state ->
            updateUiState { uiState ->
                uiState.copy(loginData = state)
            }
        }
    }

    fun showErrorPage() {
        updateUiState { loginState ->
            val state = UiState.Error(UnauthorizedError.Common)
            loginState.copy(loginData = state)
        }
    }
}