package com.plub.data.api

import com.plub.data.dto.login.SocialLoginResponse
import com.plub.data.dto.login.SocialLoginRequest
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.login.AdminLoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @POST(Endpoints.AUTH.SOCIAL_LOGIN)
    suspend fun socialLogin(@Body request: SocialLoginRequest) : Response<ApiResponse<SocialLoginResponse>>

    @POST(Endpoints.AUTH.SOCIAL_LOGIN_ADMIN)
    suspend fun adminLogin(@Body request: AdminLoginRequest) : Response<ApiResponse<SocialLoginResponse>>
}