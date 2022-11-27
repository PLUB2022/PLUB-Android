package com.plub.domain.model.vo.login

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.SocialLoginType

data class SocialLoginResponseVo(
    val authCode:String = "",
): DomainModel()