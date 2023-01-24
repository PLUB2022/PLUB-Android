package com.plub.data.api

import com.plub.data.dto.account.MyInfoResponse
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

interface AccountApi {

    @GET(Endpoints.ACCOUNT.FETCH_MY_INFO)
    suspend fun fetchMyInfo(): Response<ApiResponse<MyInfoResponse>>
}