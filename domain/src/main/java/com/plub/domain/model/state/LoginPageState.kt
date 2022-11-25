package com.plub.domain.model.state

import com.plub.domain.UiState
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.model.vo.login.SocialLoginResponseVo

data class LoginPageState(
    val authCode:String = ""
): PageState