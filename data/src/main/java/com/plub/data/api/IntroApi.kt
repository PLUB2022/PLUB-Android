package com.plub.data.api

import com.plub.data.dto.sample.SampleLoginResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response

interface IntroApi {
    suspend fun trySampleLogin() : Response<ApiResponse<SampleLoginResponse>>
}