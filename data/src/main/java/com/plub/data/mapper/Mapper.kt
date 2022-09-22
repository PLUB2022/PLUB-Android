package com.plub.data.mapper

import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleLogin

object Mapper {
    fun mapperToSampleLogin(sampleLoginResponse: SampleLoginResponse): SampleLogin {
        return SampleLogin(sampleLoginResponse.login, sampleLoginResponse.register)
    }
}