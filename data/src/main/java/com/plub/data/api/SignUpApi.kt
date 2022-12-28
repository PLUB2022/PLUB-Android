package com.plub.data.api

import com.plub.data.dto.signUp.NicknameCheckResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpApi {
    @GET(Endpoints.ACCOUNT.NICKNAME_CHECK)
    suspend fun nicknameCheck(@Path("nickname") nickname:String) : Response<ApiResponse<NicknameCheckResponse>>
}