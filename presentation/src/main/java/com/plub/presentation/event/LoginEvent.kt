package com.plub.presentation.event

import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo

sealed class LoginEvent : Event {
    object SignInGoogle : LoginEvent()
    object SignInKakao : LoginEvent()
    object SignInKakaoEmail : LoginEvent()
    object GoToSignUp : LoginEvent()
    object GoToMain : LoginEvent()
    data class GoToTerms(val type:TermsType) : LoginEvent()
}
