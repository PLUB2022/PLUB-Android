package com.plub.data.api

import com.plub.data.dto.hobby.AllHobbiesResponse
import com.plub.data.base.ApiResponse
import com.plub.data.dto.hobby.SubHobbiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HobbyApi {
    @GET(Endpoints.CATEGORY.GET_ALL_CATEGORIES)
    suspend fun allCategories() : Response<ApiResponse<AllHobbiesResponse>>

    @GET(Endpoints.CATEGORY.GET_SUB_CATEGORIES)
    suspend fun subCategories(
        @Path("categoryId") request : Int
    ) : Response<ApiResponse<SubHobbiesResponse>>
}