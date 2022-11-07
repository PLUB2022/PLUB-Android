package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleLogin

object SampleLoginMapper: Mapper<SampleLoginResponse, SampleLogin>() {

    override fun mapFromEntity(type: SampleLoginResponse):SampleLogin {
        return type.run {
            SampleLogin(login, register)
        }
    }
}