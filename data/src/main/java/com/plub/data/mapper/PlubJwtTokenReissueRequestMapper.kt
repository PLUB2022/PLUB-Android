package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.login.SocialLoginRequest
import com.plub.data.dto.sample.JWTTokenReIssueRequest
import com.plub.domain.model.vo.jwt_token.JWTTokenReIssueRequestVo
import com.plub.domain.model.vo.login.SocialLoginRequestVo

object PlubJwtTokenReissueRequestMapper: Mapper.RequestMapper<JWTTokenReIssueRequest, JWTTokenReIssueRequestVo> {
    override fun mapModelToDto(type: JWTTokenReIssueRequestVo): JWTTokenReIssueRequest {
        return type.run {
            JWTTokenReIssueRequest(refreshToken)
        }
    }
}