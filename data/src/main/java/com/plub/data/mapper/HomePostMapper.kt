package com.plub.data.mapper

import com.plub.data.dto.plubJwt.SampleHomePostRequest
import com.plub.domain.model.vo.home.HomePostRequestVo

object HomePostMapper {
    fun mapperToSampleRequest(vo: HomePostRequestVo): SampleHomePostRequest {
        return SampleHomePostRequest(vo.authCode, vo.isLoginSuccess)
    }
}
