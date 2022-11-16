package com.plub.data.api

import com.plub.data.dto.login.SocialLoginResponse
import com.plub.data.dto.login.SocialLoginRequest
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST(Endpoints.TEST.LOGIN_TEST)
    suspend fun socialLogin(@Body request: SocialLoginRequest) : Response<ApiResponse<SocialLoginResponse>>
}