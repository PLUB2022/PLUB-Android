package com.plub.data.api

import com.plub.data.dto.categorylistdata.CategoryListData
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringResponse
import com.plub.data.dto.recruitdetail.RecruitDetailResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BrowseApi {
    @GET(Endpoints.PLUBBING.FETCH_RECOMMENDATION_GATHERING)
    suspend fun fetchRecommendationGathering(
        @Query("pageNum") pageNum : Int
    ) : Response<ApiResponse<RecommendationGatheringResponse>>

    @GET(Endpoints.PLUBBING.FETCH_CATEGORIES_GATHERING)
    suspend fun fetchCategoriesGathering(
        @Path("categoryId") categoryId : Int,
        @Query("pageNum") pageNum : Int
    ) : Response<ApiResponse<RecommendationGatheringResponse>>

    @GET(Endpoints.PLUBBING.FETCH_DETAIL_RECRUIT)
    suspend fun fetchRecruitDetail(
        @Path("plubbingId") plubbingId : Int,
    ) : Response<ApiResponse<RecruitDetailResponse>>

    @GET(Endpoints.CATEGORY.GET_BIG_CATEGORIES)
    suspend fun fetchCategoryList() : Response<ApiResponse<CategoryListData>>
}