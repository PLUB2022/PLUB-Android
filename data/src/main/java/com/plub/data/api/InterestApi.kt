package com.plub.data.api

import com.plub.data.dto.registerinterest.InterestRequest
import com.plub.data.dto.registerinterest.RegisterInterestResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InterestApi {

    @POST(Endpoints.ACCOUNT.REGIST_INTEREST)
    suspend fun registerHobby(@Body categoryId : InterestRequest) : Response<ApiResponse<RegisterInterestResponse>>

    @GET(Endpoints.ACCOUNT.BROWSE_INTEREST)
    suspend fun browseRegisteredInterest() : Response<ApiResponse<RegisterInterestResponse>>
}