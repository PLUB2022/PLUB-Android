package com.plub.data.mapper

import com.plub.data.model.SampleAccountResponse
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleLogin
import retrofit2.Response

object Mapper {
    fun mapperToSampleLogin(sampleLoginResponse: SampleLoginResponse): SampleLogin {
        return SampleLogin(sampleLoginResponse.login, sampleLoginResponse.register)
    }

    fun mapperToSampleAccount(sampleAccountResponse: Response<SampleAccountResponse>) : SampleAccount{
        return SampleAccount(sampleAccountResponse.body()!!.status, sampleAccountResponse.body()!!.data, sampleAccountResponse.body()!!.message)
    }
}