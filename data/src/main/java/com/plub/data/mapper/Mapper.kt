package com.plub.data.mapper

import com.plub.data.model.SampleAccountResponse
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleLogin
import retrofit2.Call
import retrofit2.Response

object Mapper {
    fun mapperToSampleLogin(sampleLoginResponse: SampleLoginResponse): SampleLogin {

        return SampleLogin(sampleLoginResponse.login, sampleLoginResponse.register)
    }

    fun mapperToSampleAccount(sampleAccountResponse: SampleAccountResponse) : SampleAccount{
        return SampleAccount(sampleAccountResponse.status, sampleAccountResponse.data, sampleAccountResponse.message)
    }
}