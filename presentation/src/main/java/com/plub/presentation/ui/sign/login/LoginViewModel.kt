package com.plub.presentation.ui.sign.login

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.plub.domain.error.LoginError
import com.plub.domain.model.enums.SocialLoginType
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.usecase.PostSocialLoginUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.LoginPageState
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val postSocialLoginUseCase: PostSocialLoginUseCase
) : BaseViewModel<LoginPageState>(LoginPageState()) {

    private val _signInGoogle = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val signInGoogle: SharedFlow<Unit> = _signInGoogle.asSharedFlow()

    private val _signInKakao = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val signInKakao: SharedFlow<Unit> = _signInKakao.asSharedFlow()

    init {
        updateUiState { uiState ->
            uiState.copy(termsText = getTermsString())
        }
    }

    fun onClickGoogleLogin() {
        viewModelScope.launch {
            _signInGoogle.emit(Unit)
        }
    }

    fun onClickKakaoLogin() {
        viewModelScope.launch {
            _signInKakao.emit(Unit)
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
            val request = SocialLoginRequestVo(socialLoginType, authCode, true)
            postSocialLoginUseCase(request).collect { state ->
                inspectUiState(state, ::handleLoginSuccess) { _ , individual ->
                    handleLoginError(individual as LoginError)
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResponseVo: SocialLoginResponseVo) {
        updateUiState { uiState ->
            uiState.copy(authCode = loginResponseVo.authCode)
        }
    }

    private fun handleLoginError(loginError: LoginError) {
        when(loginError) {
            is LoginError.NotFoundUserAccount -> {
                updateUiState { uiState ->
                    uiState.copy(authCode = loginError.msg)
                }
            }
            else -> Unit
        }
    }

    private fun getTermsString():SpannableString {
        val termsText = getStringResource(R.string.login_plub_terms)
        val privacyPosition = getTermsPosition(termsText, TermsType.PRIVACY)
        val servicePosition = getTermsPosition(termsText, TermsType.SERVICE)

        val termsSpannableString = SpannableString(termsText).apply {
            setSpan(getTermsClickableSpan(TermsType.PRIVACY),privacyPosition.first, privacyPosition.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(getTermsClickableSpan(TermsType.SERVICE),servicePosition.first, servicePosition.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return termsSpannableString
    }

    private fun getTermsPosition(termsString:String, termsType: TermsType):Pair<Int,Int> {
        val termsTypeRes = when(termsType) {
            TermsType.SERVICE -> R.string.word_terms_service
            TermsType.PRIVACY -> R.string.word_terms_privacy
            else -> R.string.word_terms_privacy
        }
        val termsTypeString = getStringResource(termsTypeRes)
        val termsTypeStartIdx = termsString.indexOf(termsTypeString)
        val termsTypeEndIdx = termsTypeStartIdx + termsTypeString.length

        return Pair(termsTypeStartIdx, termsTypeEndIdx)
    }

    private fun getTermsClickableSpan(termsType: TermsType):ClickableSpan {
        return object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = getColorResource(R.color.color_5f5ff9)
                textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            override fun onClick(p0: View) {
                goToTerms(termsType)
            }
        }
    }

    private fun goToTerms(termsType: TermsType) {
        updateUiState { uiState ->
            uiState.copy(authCode = termsType.toString())
        }
    }

    private fun getStringResource(res: Int): String {
        return resourceProvider.getString(res)
    }

    private fun getColorResource(res: Int): Int {
        return resourceProvider.getColor(res)
    }
}