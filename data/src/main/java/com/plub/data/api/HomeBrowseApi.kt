package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.categorylistdata.CategoryListResponse
import com.plub.data.dto.plub.PlubCardListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeBrowseApi {
    @GET(Endpoints.PLUBBING.FETCH_RECOMMENDATION_GATHERING)
    suspend fun fetchRecommendationGathering(
        @Query("pageNum") pageNum : Int
    ) : Response<ApiResponse<PlubCardListResponse>>

    @GET(Endpoints.PLUBBING.FETCH_CATEGORIES_GATHERING)
    suspend fun fetchCategoriesGathering(
        @Path("categoryId") categoryId : Int,
        @Query("sort") sort : String,
        @Query("pageNum") pageNum : Int
    ) : Response<ApiResponse<PlubCardListResponse>>

    @GET(Endpoints.CATEGORY.GET_BIG_CATEGORIES)
    suspend fun fetchCategoryList() : Response<ApiResponse<CategoryListResponse>>
}