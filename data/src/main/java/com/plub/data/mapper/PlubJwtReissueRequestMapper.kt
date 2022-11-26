package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.sample.PlubJwtReIssueRequest
import com.plub.domain.model.vo.jwt_token.PlubJwtReIssueRequestVo

object PlubJwtReissueRequestMapper: Mapper.RequestMapper<PlubJwtReIssueRequest, PlubJwtReIssueRequestVo> {
    override fun mapModelToDto(type: PlubJwtReIssueRequestVo): PlubJwtReIssueRequest {
        return type.run {
            PlubJwtReIssueRequest(refreshToken)
        }
    }
}