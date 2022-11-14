package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.SampleLoginResponse
import com.plub.domain.model.vo.login.SampleLogin

object SampleLoginMapper: Mapper<SampleLoginResponse, SampleLogin>() {
    override fun mapModelToDto(type: SampleLogin): SampleLoginResponse {
        throw NotImplementedError()
    }

    override fun mapDtoToModel(type: SampleLoginResponse): SampleLogin {
        return type.run {
            SampleLogin(login, register)
        }
    }
}