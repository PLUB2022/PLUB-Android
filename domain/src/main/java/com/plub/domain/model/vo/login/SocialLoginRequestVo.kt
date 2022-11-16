package com.plub.domain.model.vo.login

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.SocialLoginType

data class SocialLoginRequestVo(
    val socialLoginType: SocialLoginType,
    val authCode:String,
    val isLoginSuccess:Boolean
): DomainModel()