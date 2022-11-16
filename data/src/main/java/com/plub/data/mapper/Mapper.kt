package com.plub.data.mapper

import android.util.Log
import com.plub.data.model.SampleAccountResponse
import com.plub.data.model.SampleAuthInfoResponse
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleAuthInfo
import com.plub.domain.model.vo.login.SampleLogin
import retrofit2.Call
import retrofit2.Response

object Mapper {
    fun mapperToSampleLogin(sampleLoginResponse: SampleLoginResponse): SampleLogin {

        return SampleLogin(sampleLoginResponse.login, sampleLoginResponse.register)
    }

    fun mapperToSampleAccount(sampleAccountResponse: SampleAccountResponse) : SampleAccount{
        return SampleAccount(sampleAccountResponse.status, sampleAccountResponse.data, sampleAccountResponse.message)
    }

    fun mapperToSampleAuthInfo(sampleAuthInfoResponse: SampleAuthInfoResponse) : SampleAuthInfo{
        Log.d("RETROFITTEEST_TAG", sampleAuthInfoResponse.data.toString())
        return SampleAuthInfo(
            sampleAuthInfoResponse.data.birthday,
            sampleAuthInfoResponse.data.email,
            sampleAuthInfoResponse.data.gender,
            sampleAuthInfoResponse.data.introduce,
            sampleAuthInfoResponse.data.nickname,
            "임시 테스트",
            sampleAuthInfoResponse.data.socialType)
    }
}