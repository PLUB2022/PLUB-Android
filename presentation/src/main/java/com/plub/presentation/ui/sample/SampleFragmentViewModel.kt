package com.plub.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.UnauthorizedError
import com.plub.domain.model.state.SampleLoginPageState
import com.plub.domain.repository.PrefDataStoreRepository
import com.plub.domain.usecase.TrySampleLoginUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase,
    private val prefDataStoreRepository: PrefDataStoreRepository
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

    fun saveStringByDataStore() = viewModelScope.launch {
        prefDataStoreRepository.setString("test", acTokenInput.value)
    }

    fun saveBooleanByDataStore() = viewModelScope.launch {
        prefDataStoreRepository.setBoolean("test", true)
    }

    fun getStringByDataStore() = viewModelScope.launch {
        _acToken.value = prefDataStoreRepository.getString("test").getOrDefault("")
    }

    fun getBooleanByDataStore() = viewModelScope.launch {
        _reToken.value = prefDataStoreRepository.getBoolean("test").getOrDefault(false).toString()
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