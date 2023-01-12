package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.login.SocialLoginResponse
import com.plub.domain.model.vo.login.SocialLoginResponseVo

object SocialLoginResponseMapper: Mapper.ResponseMapper<SocialLoginResponse, SocialLoginResponseVo> {

    override fun mapDtoToModel(type: SocialLoginResponse?): SocialLoginResponseVo {
        return type?.run {
            SocialLoginResponseVo(
                accessToken = accessToken,
                refreshToken = refreshToken,
                signToken = signToken,
            )
        }?: SocialLoginResponseVo()
    }
}