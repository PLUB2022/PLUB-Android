package com.plub.domain.model.vo.jwt_token

import com.plub.domain.base.DomainModel

data class PlubJwtTokenVo (
    val data : PlubJwtTokenData?
): DomainModel()

data class PlubJwtTokenData (
    val accessToken : String,
    val refreshToken : String
)