package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.sample.PlubJwtTokenResponse
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenResponseVo

object PlubJwtTokenMapper: Mapper.ResponseMapper<PlubJwtTokenResponse, PlubJwtTokenResponseVo> {

    override fun mapDtoToModel(type: PlubJwtTokenResponse?): PlubJwtTokenResponseVo {
        return type?.run {
            PlubJwtTokenResponseVo(accessToken, refreshToken)
        }?: PlubJwtTokenResponseVo("","")
    }
}