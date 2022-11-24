package com.plub.presentation.ui.sample

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.error.UnauthorizedError
import com.plub.domain.model.state.SampleLoginPageState
import com.plub.domain.model.vo.datastore.DataStoreBooleanVo
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.result.LoginFailure
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetBooleanFromDataStoreUseCase
import com.plub.domain.usecase.SetBooleanFromDataStoreUseCase
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.domain.usecase.TrySampleLoginUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(
    private val trySampleLoginUseCase: TrySampleLoginUseCase,
    private val getBooleanFromDataStoreUseCase: GetBooleanFromDataStoreUseCase,
    private val setBooleanFromDataStoreUseCase: SetBooleanFromDataStoreUseCase,
    private val testPostHomeUseCase: TestPostHomeUseCase
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

    fun saveBooleanByDataStore() = viewModelScope.launch {
        setBooleanFromDataStoreUseCase(DataStoreBooleanVo("testBoolean", !reToken.value.toBoolean())).collect()
    }

    fun getBooleanByDataStore() = viewModelScope.launch {
        getBooleanFromDataStoreUseCase("testBoolean").collect { state ->
            _reToken.value = when (state) {
                is UiState.Loading -> "loading"
                is UiState.Success -> state.data.toString()
                is UiState.Error -> state.error.toString()
            }
        }
    }

    fun trySampleLogin() = viewModelScope.launch {
//        trySampleLoginUseCase.invoke(Unit).collect { state ->
//            updateUiState { uiState ->
//                uiState.copy(loginData = state)
//            }
//        }

        testPostHomeUseCase.invoke(HomePostRequestVo("testcode", false)).collect { state ->
            when(state){
                is UiState.Loading -> Log.d("테스트용", "로딩")
                is UiState.Success -> Log.d("테스트용", "${state.successOrNull()!!.authCode}")
                is UiState.Error -> Log.d("테스트용", "실패")
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