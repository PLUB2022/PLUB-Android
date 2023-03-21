package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.plub.CategoryGatheringBodyRequest
import com.plub.data.dto.plub.PlubCardListResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {

    companion object{
        private const val QUERY_PAGE_NUM = "pageNum"
        private const val PATH_CATEGORY_ID = "categoryId"
        private const val QUERY_SORT = "sort"
    }

    @GET(Endpoints.PLUBBING.FETCH_RECOMMENDATION_GATHERING)
    suspend fun fetchRecommendationGathering(
        @Query(QUERY_PAGE_NUM) pageNum : Int
    ) : Response<ApiResponse<PlubCardListResponse>>

    @POST(Endpoints.PLUBBING.FETCH_CATEGORIES_GATHERING)
    suspend fun fetchCategoriesGathering(
        @Path(PATH_CATEGORY_ID) categoryId : Int,
        @Query(QUERY_SORT) sort : String,
        @Query(QUERY_PAGE_NUM) pageNum : Int,
        @Body plubbingCardRequest : CategoryGatheringBodyRequest
    ) : Response<ApiResponse<PlubCardListResponse>>
}