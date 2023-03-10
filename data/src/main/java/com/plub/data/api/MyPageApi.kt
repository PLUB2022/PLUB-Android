package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageApi {
    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_GATHERING)
    suspend fun getMyGathering(
        @Query("status") status: String
    ): Response<ApiResponse<NicknameCheckResponse>>
}