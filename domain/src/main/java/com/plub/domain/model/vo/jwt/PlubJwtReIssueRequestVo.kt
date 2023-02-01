package com.plub.domain.model.vo.jwt

import com.plub.domain.model.DomainModel

data class PlubJwtReIssueRequestVo(
    val refreshToken : String
): DomainModel
