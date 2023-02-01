package com.plub.data.api

import com.plub.data.dto.account.MyInfoResponse
import com.plub.data.base.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountApi {

    @GET(Endpoints.ACCOUNT.FETCH_MY_INFO)
    suspend fun fetchMyInfo(): Response<ApiResponse<MyInfoResponse>>
}