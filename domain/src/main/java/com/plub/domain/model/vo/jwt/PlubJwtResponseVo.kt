package com.plub.domain.model.vo.jwt

import com.plub.domain.base.DomainModel

data class PlubJwtResponseVo (
    val accessToken : String,
    val refreshToken : String
):DomainModel() {
    val isTokenValid = accessToken.isNotBlank() && refreshToken.isNotBlank()
}