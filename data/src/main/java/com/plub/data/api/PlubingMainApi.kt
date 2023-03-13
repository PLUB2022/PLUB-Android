package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.plub.PlubingMainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlubingMainApi {

    @GET(Endpoints.PLUBBING.PLUBING_MAIN)
    suspend fun getPlubingMain(@Path("plubbingId") plubbingId:Int): Response<ApiResponse<PlubingMainResponse>>
}