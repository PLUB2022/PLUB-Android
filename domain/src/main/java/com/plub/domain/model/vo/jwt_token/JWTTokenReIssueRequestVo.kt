package com.plub.domain.model.vo.jwt_token

import com.plub.domain.base.DomainModel

data class JWTTokenReIssueRequestVo(
    val refreshToken : String
):DomainModel()
