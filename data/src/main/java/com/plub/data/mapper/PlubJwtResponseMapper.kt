package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.sample.PlubJwtResponse
import com.plub.domain.model.vo.jwt_token.PlubJwtResponseVo

object PlubJwtResponseMapper: Mapper.ResponseMapper<PlubJwtResponse, PlubJwtResponseVo> {

    override fun mapDtoToModel(type: PlubJwtResponse?): PlubJwtResponseVo {
        return type?.run {
            PlubJwtResponseVo(accessToken, refreshToken)
        }?: PlubJwtResponseVo("","")
    }
}