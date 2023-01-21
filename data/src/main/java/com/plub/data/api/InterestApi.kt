package com.plub.data.api

import com.plub.data.dto.InterestRequset
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface InterestApi {

    @PUT(Endpoints.ACCOUNT.REGIST_INTEREST)
    suspend fun registerHobby(@Body categoryId : InterestRequset) : Unit

    @GET(Endpoints.ACCOUNT.BROWSE_INTEREST)
    suspend fun browseRegistedInterest()
}