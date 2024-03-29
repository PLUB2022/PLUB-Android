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
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.plub.domain.error.LoginError
import com.plub.domain.model.enums.SocialLoginType
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.jwt.SavePlubJwtRequestVo
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.usecase.FetchMyInfoUseCase
import com.plub.domain.usecase.PostAdminLoginUseCase
import com.plub.domain.usecase.PostSocialLoginUseCase
import com.plub.domain.usecase.SavePlubAccessTokenAndRefreshTokenUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.DataStoreUtil
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.PlubUser
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val postAdminLoginUseCase: PostAdminLoginUseCase,
    val postSocialLoginUseCase: PostSocialLoginUseCase,
    val savePlubAccessTokenAndRefreshTokenUseCase: SavePlubAccessTokenAndRefreshTokenUseCase,
    val dataStoreUtil: DataStoreUtil,
    val fetchMyInfoUseCase: FetchMyInfoUseCase
) : BaseViewModel<LoginPageState>(LoginPageState()) {

    init {
        updateUiState { uiState ->
            uiState.copy(termsText = getTermsString())
        }
    }

    fun onAdminLogin() {
        viewModelScope.launch {
            postAdminLoginUseCase(Unit).collect {
                inspectUiState(it, ::handleLoginSuccess)
            }
        }
    }

    fun onClickGoogleLogin() {
        emitEventFlow(LoginEvent.SignInGoogle)
    }

    fun onClickKakaoLogin() {
        emitEventFlow(LoginEvent.SignInKakao)
    }

    fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account.serverAuthCode?.let {
                val request = SocialLoginRequestVo(SocialLoginType.GOOGLE, authCode = it)
                socialLogin(request)
            }
        } catch (e: ApiException) {
            PlubLogger.logD("구글로그인", "실패 : ${e.statusCode}")
        }
    }

    fun handleKakaoSignInAppResult(token: OAuthToken?, error: Throwable?) {
        token?.let {
            val request = SocialLoginRequestVo(SocialLoginType.KAKAO, accessToken = it.accessToken)
            socialLogin(request)
        } ?: run {
            val userCancel = error is ClientError && error.reason == ClientErrorCause.Cancelled
            if (!userCancel) viewModelScope.launch {
                emitEventFlow(LoginEvent.SignInKakaoEmail)
            }
        }
    }

    fun handleKakaoSignInEmailResult(token: OAuthToken?, error: Throwable?) {
        token?.let {
            val request = SocialLoginRequestVo(SocialLoginType.KAKAO, accessToken = it.accessToken)
            socialLogin(request)
        }
    }

    fun alreadyGoogleSignIn(account: GoogleSignInAccount) {
        account.serverAuthCode?.let {
            val request = SocialLoginRequestVo(SocialLoginType.GOOGLE, it)
            socialLogin(request)
        }
    }

    private fun socialLogin(request: SocialLoginRequestVo) {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val requestWithFcmToken = request.copy(fcmToken = token)
            viewModelScope.launch {
                postSocialLoginUseCase(requestWithFcmToken).collect { state ->
                    inspectUiState(state, ::handleLoginSuccess, { data, individual ->
                        handleLoginError(data, individual as LoginError)
                    })
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResponseVo: SocialLoginResponseVo) {
        val savePlubJwtRequestVo =
            SavePlubJwtRequestVo(loginResponseVo.accessToken, loginResponseVo.refreshToken)
        savePlubToken(savePlubJwtRequestVo) {
            fetchMyInfo {
                goToMain()
            }
        }
    }

    private fun savePlubToken(saveRequestVo: SavePlubJwtRequestVo, saveCallback: () -> Unit) {
        viewModelScope.launch {
            savePlubAccessTokenAndRefreshTokenUseCase(saveRequestVo).collect {
                if (it) saveCallback.invoke()
            }
        }
    }

    private fun fetchMyInfo(onSuccess: () -> Unit) {
        viewModelScope.launch {
            fetchMyInfoUseCase(Unit).collect { state ->
                inspectUiState(state, succeedCallback = { info ->
                    PlubUser.updateInfo(info)
                    onSuccess()
                })
            }
        }
    }

    private fun handleLoginError(data: SocialLoginResponseVo?, loginError: LoginError) {
        when (loginError) {
            is LoginError.NeedSignUp -> {
                data?.let {
                    saveSignUpToken(it.signToken) {
                        goToSignUp()
                    }
                }
            }
            else -> Unit
        }
    }

    private fun saveSignUpToken(signUpToken: String, saveCallback: () -> Unit) {
        viewModelScope.launch {
            dataStoreUtil.setSignUpToken(signUpToken).collect {
                inspectUiState(it, {
                    saveCallback.invoke()
                })
            }
        }
    }

    private fun getTermsString(): SpannableString {
        val termsText = getStringResource(R.string.login_plub_terms)
        val privacyPosition = getTermsPosition(termsText, TermsType.PRIVACY)
        val servicePosition = getTermsPosition(termsText, TermsType.SERVICE)

        val termsSpannableString = SpannableString(termsText).apply {
            setSpan(getTermsClickableSpan(TermsType.PRIVACY), privacyPosition.first, privacyPosition.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(getTermsClickableSpan(TermsType.SERVICE), servicePosition.first, servicePosition.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return termsSpannableString
    }

    private fun getTermsPosition(termsString: String, termsType: TermsType): Pair<Int, Int> {
        val termsTypeRes = when (termsType) {
            TermsType.SERVICE -> R.string.word_terms_service
            TermsType.PRIVACY -> R.string.word_terms_privacy
            else -> R.string.word_terms_privacy
        }
        val termsTypeString = getStringResource(termsTypeRes)
        val termsTypeStartIdx = termsString.indexOf(termsTypeString)
        val termsTypeEndIdx = termsTypeStartIdx + termsTypeString.length

        return Pair(termsTypeStartIdx, termsTypeEndIdx)
    }

    private fun getTermsClickableSpan(termsType: TermsType): ClickableSpan {
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

    private fun goToSignUp() {
        emitEventFlow(LoginEvent.GoToSignUp)
    }

    private fun goToMain() {
        emitEventFlow(LoginEvent.GoToMain)
    }

    private fun goToTerms(termsType: TermsType) {
        emitEventFlow(LoginEvent.GoToTerms(termsType))
    }

    private fun getStringResource(res: Int): String {
        return resourceProvider.getString(res)
    }

    private fun getColorResource(res: Int): Int {
        return resourceProvider.getColor(res)
    }
}