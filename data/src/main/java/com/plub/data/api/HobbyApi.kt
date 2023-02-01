package com.plub.data.api

import com.plub.data.dto.hobby.AllHobbiesResponse
import com.plub.data.base.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface HobbyApi {
    @GET(Endpoints.CATEGORY.GET_ALL_CATEGORIES)
    suspend fun allCategories() : Response<ApiResponse<AllHobbiesResponse>>
}