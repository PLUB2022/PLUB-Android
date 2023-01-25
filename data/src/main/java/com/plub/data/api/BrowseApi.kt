package com.plub.data.api

import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringResponse
import com.plub.data.dto.categorylistdata.CategoryListData
import com.plub.data.dto.recruitdetail.RecruitDetailResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BrowseApi {
    @GET("/api/plubbings/recommendation")
    suspend fun browseRecommendationGathering(
        @Query("pageNum") pageNum : Int,
        @Header("accessToken") accessToken : String
    ) : Response<ApiResponse<RecommendationGatheringResponse>>

    @GET("/api/plubbings/categories/{categoryId}")
    suspend fun browseCategoriesGathering(
        @Path("categoryId") categoryId : Int,
        @Query("pageNum") pageNum : Int,
        @Header("accessToken") accessToken : String
    ) : Response<ApiResponse<RecommendationGatheringResponse>>

    @GET("/api/plubbings/{plubbingId}/recruit")
    suspend fun browseRecruitDetail(
        @Path("plubbingId") plubbingId : Int,
        @Header("accessToken") accessToken : String
    ) : Response<ApiResponse<RecruitDetailResponse>>


    @GET("/api/categories")
    suspend fun browseCategoryList() : Response<ApiResponse<CategoryListData>>
}