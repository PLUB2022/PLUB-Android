package com.plub.domain.model.vo.jwt_token

import com.plub.domain.base.DomainModel

data class PlubJwtTokenResponseVo (
    val accessToken : String,
    val refreshToken : String
):DomainModel() {
    val isTokenValid = accessToken.isNotBlank() && refreshToken.isNotBlank()
}