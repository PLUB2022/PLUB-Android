package com.plub.data.api

import com.plub.data.dto.plubJwt.PlubJwtResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import com.plub.data.dto.signUp.SignUpRequest
import com.plub.data.util.ApiResponse
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignUpApi {
    @GET(Endpoints.ACCOUNT.NICKNAME_CHECK)
    suspend fun nicknameCheck(@Path("nickname") nickname: String): Response<ApiResponse<NicknameCheckResponse>>

    @POST(Endpoints.AUTH.SIGN_UP)
    suspend fun signUp(@Body request: SignUpRequest): Response<ApiResponse<PlubJwtResponse>>
}