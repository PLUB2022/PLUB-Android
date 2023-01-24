package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.PlubJwtReIssueRequest
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo

object PlubJwtReissueRequestMapper: Mapper.RequestMapper<PlubJwtReIssueRequest, PlubJwtReIssueRequestVo> {
    override fun mapModelToDto(type: PlubJwtReIssueRequestVo): PlubJwtReIssueRequest {
        return type.run {
            PlubJwtReIssueRequest(refreshToken)
        }
    }
}