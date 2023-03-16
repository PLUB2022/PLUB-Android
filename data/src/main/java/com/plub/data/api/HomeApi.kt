package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.plub.CategoryGatheringBodyRequest
import com.plub.data.dto.plub.PlubCardListResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {
    @GET(Endpoints.PLUBBING.FETCH_RECOMMENDATION_GATHERING)
    suspend fun fetchRecommendationGathering(
        @Query("pageNum") pageNum : Int
    ) : Response<ApiResponse<PlubCardListResponse>>

    @POST(Endpoints.PLUBBING.FETCH_CATEGORIES_GATHERING)
    suspend fun fetchCategoriesGathering(
        @Path("categoryId") categoryId : Int,
        @Query("sort") sort : String,
        @Query("pageNum") pageNum : Int,
        @Body plubbingCardRequest : CategoryGatheringBodyRequest
    ) : Response<ApiResponse<PlubCardListResponse>>
}