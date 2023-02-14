package com.plub.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.jwt.SavePlubJwtRequestVo
import com.plub.domain.usecase.GetPlubRefreshTokenUseCase
import com.plub.domain.usecase.PostReIssueTokenUseCase
import com.plub.domain.usecase.SavePlubAccessTokenAndRefreshTokenUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getPlubRefreshTokenUseCase: GetPlubRefreshTokenUseCase,
    private val savePlubAccessTokenAndRefreshTokenUseCase: SavePlubAccessTokenAndRefreshTokenUseCase,
    private val reIssueTokenUseCase: PostReIssueTokenUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {

    fun reIssueTokenAfterMoveActivity() {
        viewModelScope.launch {
            val refreshToken = getPlubRefreshTokenUseCase(Unit).first()

            /*
             * BaseViewModel의 inspectUiState를 사용하면 로딩 프로세스가 보이기 때문에 사용하지 않음.
             */
            reIssueTokenUseCase(PlubJwtReIssueRequestVo(refreshToken)).collect { uiState ->
                when(uiState) {
                    is UiState.Loading -> { }
                    is UiState.Success -> {
                        handlePostReIssueTokenSuccess(uiState.data)
                    }
                    is UiState.Error -> {
                        emitEventFlow(SplashEvent.GoToSignUp)
                    }
                }
            }
        }
    }

    private fun handlePostReIssueTokenSuccess(token: PlubJwtResponseVo) {
        if(token.isTokenValid) {
            savePlubToken(SavePlubJwtRequestVo(token.accessToken, token.refreshToken)) {
                emitEventFlow(SplashEvent.GoToMain)
            }
        }
        else emitEventFlow(SplashEvent.GoToSignUp)
    }

    private fun savePlubToken(saveRequestVo: SavePlubJwtRequestVo, saveCallback: () -> Unit) {
        viewModelScope.launch {
            savePlubAccessTokenAndRefreshTokenUseCase(saveRequestVo).collect {
                if (it) saveCallback.invoke()
            }
        }
    }
}