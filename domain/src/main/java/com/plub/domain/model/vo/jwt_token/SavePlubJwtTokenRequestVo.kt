package com.plub.domain.model.vo.jwt_token

import com.plub.domain.base.DomainModel

data class SavePlubJwtTokenRequestVo (
    val accessToken : String,
    val refreshToken : String
):DomainModel()