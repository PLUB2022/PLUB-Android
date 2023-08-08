package com.plub.presentation.ui.sign.login

import com.plub.domain.model.enums.TermsType
import com.plub.presentation.ui.Event

sealed class LoginEvent : Event {
    object SignInGoogle : LoginEvent()
    object SignInKakao : LoginEvent()
    object SignInKakaoEmail : LoginEvent()
    object GoToSignUp : LoginEvent()
    object GoToMain : LoginEvent()

    object FailSocialLogin : LoginEvent()
    object StoppedAccountDialog : LoginEvent()
    data class GoToTerms(val type:TermsType) : LoginEvent()
}
