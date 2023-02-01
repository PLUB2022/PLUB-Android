package com.plub.domain.model.vo.jwt

import com.plub.domain.model.DomainModel

data class SavePlubJwtRequestVo (
    val accessToken : String,
    val refreshToken : String
): DomainModel