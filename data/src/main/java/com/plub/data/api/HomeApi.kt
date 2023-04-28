package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.plub.CategoryGatheringBodyRequest
import com.plub.data.dto.plub.PlubCardListResponse
import com.plub.data.dto.registerHobbies.RegisterHobbiesRequest
import com.plub.data.dto.registerHobbies.RegisterHobbiesResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {

    companion object{
        private const val QUERY_CURSOR_ID = "cursorId"
        private const val PATH_CATEGORY_ID = "categoryId"
        private const val QUERY_SORT = "sort"
    }

    @GET(Endpoints.PLUBBING.FETCH_RECOMMENDATION_GATHERING)
    suspend fun fetchRecommendationGathering(
        @Query(QUERY_CURSOR_ID) pageNum : Int
    ) : Response<ApiResponse<PlubCardListResponse>>

    @POST(Endpoints.PLUBBING.FETCH_CATEGORIES_GATHERING)
    suspend fun fetchCategoriesGathering(
        @Path(PATH_CATEGORY_ID) categoryId : Int,
        @Query(QUERY_SORT) sort : String,
        @Query(QUERY_CURSOR_ID) pageNum : Int,
        @Body plubbingCardRequest : CategoryGatheringBodyRequest
    ) : Response<ApiResponse<PlubCardListResponse>>


    @POST(Endpoints.ACCOUNT.REGIST_INTEREST)
    suspend fun registerHobby(@Body subCategories : RegisterHobbiesRequest) : Response<ApiResponse<RegisterHobbiesResponse>>

    @GET(Endpoints.ACCOUNT.BROWSE_INTEREST)
    suspend fun browseRegisteredInterest() : Response<ApiResponse<RegisterHobbiesResponse>>
}