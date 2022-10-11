package com.plub.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.state.SampleLoginPageState
import com.plub.domain.usecase.TrySampleLoginUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase
) : BaseViewModel<SampleLoginPageState>(SampleLoginPageState()) {

    init {
        trySampleLogin()
    }

    fun trySampleLogin() = viewModelScope.launch {
        trySampleLoginUseCase.invoke(Unit).collect { state ->
            updateUiState { loginState ->
                loginState.copy(loginData = state)
            }
        }
    }
}