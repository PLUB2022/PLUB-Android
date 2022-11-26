package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.sample.PlubJwtTokenResponse
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenData
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenVo

object PlubJwtTokenMapper: Mapper.ResponseMapper<PlubJwtTokenResponse, PlubJwtTokenVo> {

    override fun mapDtoToModel(type: PlubJwtTokenResponse?): PlubJwtTokenVo {
        return type.run {
            PlubJwtTokenVo(PlubJwtTokenData(this?.accessToken ?: "", this?.refreshToken ?: ""))
        }
    }
}