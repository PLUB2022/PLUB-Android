package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.sample.SampleLoginResponse
import com.plub.domain.model.vo.login.SampleLogin

object SampleLoginMapper: Mapper.ResponseMapper<SampleLoginResponse, SampleLogin> {
    override fun mapDtoToModel(type: SampleLoginResponse?): SampleLogin {
        return type?.run {
            SampleLogin(login, register)
        }?: SampleLogin("","")
    }
}