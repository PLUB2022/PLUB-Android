package com.plub.presentation.ui.splash

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.usecase.FetchMyInfoUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchMyInfoUseCase: FetchMyInfoUseCase
) : BaseTestViewModel<PageState.Default>() {

    override val uiState: PageState.Default = PageState.Default

    fun fetchMyInfo(pref: SharedPreferences) {
        viewModelScope.launch {
            /**
             * BaseViewModel의 inspectUiState를 사용하면 로딩 프로세스가 보이기 때문에 사용하지 않음.
             */
            fetchMyInfoUseCase(Unit).collect { uiState ->
                when(uiState) {
                    is UiState.Loading -> { }
                    is UiState.Success -> {
                        handleFetchMyInfoSuccess(uiState.data)
                    }
                    is UiState.Error -> {
                        isFirstBegin(pref)
                    }
                }
            }
        }
    }

    private fun handleFetchMyInfoSuccess(data: MyInfoResponseVo) {
        PlubUser.updateInfo(data)
        emitEventFlow(SplashEvent.GoToMain)
    }

    private fun isFirstBegin(pref: SharedPreferences){
        val first = pref.getBoolean(SplashFragment.FIRST_APP, false)
        if (!first) {
            val editor = pref.edit()
            editor.putBoolean(SplashFragment.FIRST_APP, true)
            editor.commit()
            emitEventFlow(SplashEvent.GoToOnBoarding)
        } else {
            emitEventFlow(SplashEvent.GoToSignUp)
        }
    }
}