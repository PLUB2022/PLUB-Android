package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.model.SampleHomePostResponse
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo

object HomePostResponseMapper : Mapper.ResponseMapper<SampleHomePostResponse, HomePostResponseVo>{
    override fun mapDtoToModel(type: SampleHomePostResponse?): HomePostResponseVo {
        return type.run {
            HomePostResponseVo(this!!.authCode)
        }
    }
}