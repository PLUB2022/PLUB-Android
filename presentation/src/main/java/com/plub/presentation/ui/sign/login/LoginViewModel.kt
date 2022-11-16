package com.plub.presentation.ui.sign.login

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.plub.domain.model.enums.SocialLoginType
import com.plub.domain.model.state.LoginPageState
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.result.LoginFailure
import com.plub.domain.successOrNull
import com.plub.domain.usecase.PostSocialLoginUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val postSocialLoginUseCase: PostSocialLoginUseCase
) : BaseViewModel<LoginPageState>(LoginPageState()) {

    private val _signInGoogle = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val signInGoogle: SharedFlow<Unit> = _signInGoogle.asSharedFlow()

    fun onClickGoogleLogin() {
        viewModelScope.launch {
            _signInGoogle.emit(Unit)
        }
    }

    fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account.serverAuthCode?.let {
                socialLogin(SocialLoginType.GOOGLE, it)
            }
        } catch (e: ApiException) {
            PlubLogger.logD("구글로그인", "실패 : ${e.statusCode}")
        }
    }

    fun alreadyGoogleSignIn(account:GoogleSignInAccount) {
        account.serverAuthCode?.let {
            socialLogin(SocialLoginType.GOOGLE, it)
        }
    }

    private fun socialLogin(socialLoginType: SocialLoginType, authCode: String) {
        viewModelScope.launch {
            val request = SocialLoginRequestVo(socialLoginType, authCode, false)
            postSocialLoginUseCase(request).collect { state ->
                inspectUiState(state, ::handleLoginSuccess) { _ , individual ->
                    handleLoginFailure(individual as LoginFailure)
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResponseVo: SocialLoginResponseVo) {
        updateUiState { uiState ->
            uiState.copy(authCode = loginResponseVo.authCode)
        }
    }

    private fun handleLoginFailure(loginFailure: LoginFailure) {
        when(loginFailure) {
            is LoginFailure.NotFoundUserAccount -> {
                updateUiState { uiState ->
                    uiState.copy(authCode = loginFailure.msg)
                }
            }
            else -> Unit
        }
    }
}