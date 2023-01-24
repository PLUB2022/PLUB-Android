package com.plub.domain.model.vo.jwt

import com.plub.domain.base.DomainModel

data class PlubJwtReIssueRequestVo(
    val refreshToken : String
):DomainModel()
