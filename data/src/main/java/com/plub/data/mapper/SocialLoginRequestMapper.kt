package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.login.SocialLoginRequest
import com.plub.domain.model.vo.login.SocialLoginRequestVo

object SocialLoginRequestMapper: Mapper.RequestMapper<SocialLoginRequest, SocialLoginRequestVo> {

    override fun mapModelToDto(type: SocialLoginRequestVo): SocialLoginRequest {
        return type.run {
            SocialLoginRequest(
                accessToken = this.accessToken,
                authorizationCode = authCode,
                socialType = this.socialLoginType.value
            )
        }
    }
}