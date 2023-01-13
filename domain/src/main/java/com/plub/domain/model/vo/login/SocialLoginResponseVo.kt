package com.plub.domain.model.vo.login

import com.plub.domain.base.DomainModel

data class SocialLoginResponseVo(
    val signToken:String = "",
    val accessToken:String = "",
    val refreshToken:String = ""
): DomainModel()