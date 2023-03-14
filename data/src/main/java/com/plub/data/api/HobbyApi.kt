package com.plub.data.api

import com.plub.data.dto.hobby.AllHobbiesResponse
import com.plub.data.base.ApiResponse
import com.plub.data.dto.categoryListData.CategoryListResponse
import com.plub.data.dto.hobby.SubHobbiesResponse
import com.plub.data.dto.registerHobbies.RegisterHobbiesRequest
import com.plub.data.dto.registerHobbies.RegisterHobbiesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HobbyApi {
    @GET(Endpoints.CATEGORY.GET_ALL_CATEGORIES)
    suspend fun allCategories() : Response<ApiResponse<AllHobbiesResponse>>

    @GET(Endpoints.CATEGORY.GET_SUB_CATEGORIES)
    suspend fun subCategories(
        @Path("categoryId") request : Int
    ) : Response<ApiResponse<SubHobbiesResponse>>

    @POST(Endpoints.ACCOUNT.REGIST_INTEREST)
    suspend fun registerHobby(@Body subCategories : RegisterHobbiesRequest) : Response<ApiResponse<RegisterHobbiesResponse>>

    @GET(Endpoints.ACCOUNT.BROWSE_INTEREST)
    suspend fun browseRegisteredInterest() : Response<ApiResponse<RegisterHobbiesResponse>>


    @GET(Endpoints.CATEGORY.GET_PARENT_CATEGORIES)
    suspend fun fetchCategoryList() : Response<ApiResponse<CategoryListResponse>>
}