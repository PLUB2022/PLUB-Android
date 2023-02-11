package com.plub.data.api

import com.plub.data.dto.registerhobbies.RegisterHobbiesRequest
import com.plub.data.dto.registerhobbies.RegisterHobbiesResponse
import com.plub.data.base.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InterestHobbiesApi {

    @POST(Endpoints.ACCOUNT.REGIST_INTEREST)
    suspend fun registerHobby(@Body subCategories : RegisterHobbiesRequest) : Response<ApiResponse<RegisterHobbiesResponse>>

    @GET(Endpoints.ACCOUNT.BROWSE_INTEREST)
    suspend fun browseRegisteredInterest() : Response<ApiResponse<RegisterHobbiesResponse>>
}