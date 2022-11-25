package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.model.PlubJwtTokenResponse
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenData
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenVo
import com.plub.domain.model.vo.login.SampleLogin

object PlubJwtTokenMapper: Mapper<PlubJwtTokenResponse, PlubJwtTokenVo>() {

    override fun mapFromEntity(type: PlubJwtTokenResponse): PlubJwtTokenVo {
        return type.run {
            PlubJwtTokenVo(PlubJwtTokenData(data?.accessToken ?: "", data?.refreshToken ?: ""))
        }
    }
}