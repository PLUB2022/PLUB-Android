package com.plub.data.api

import com.plub.data.dto.plub.PlubCardListResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface SearchApi {
    @GET(Endpoints.PLUBBING.RECRUIT)
    suspend fun plubSearch(@QueryMap queryMap: Map<String, String>): Response<ApiResponse<PlubCardListResponse>>
}