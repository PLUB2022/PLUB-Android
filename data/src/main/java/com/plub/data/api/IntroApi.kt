package com.plub.data.api

import com.plub.data.dto.SampleLoginResponse
import retrofit2.Response

interface IntroApi {
    suspend fun trySampleLogin() : Response<SampleLoginResponse>
}