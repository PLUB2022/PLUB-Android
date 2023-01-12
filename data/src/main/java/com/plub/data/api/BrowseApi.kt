package com.plub.data.api

import com.plub.data.model.SampleHomePostRequest
import com.plub.data.model.SampleHomePostResponse
import com.plub.data.model.categorylistdata.CategoryListData
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringData
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BrowseApi {
    @GET("/api/plubbings/recommendation")
    suspend fun browseRecommendationGathering(
        @Query("pageNum") pageNum : Int,
        @Header("accessToken") accessToken : String
    ) : Response<ApiResponse<RecommendationGatheringData>>

    @GET("/api/categories")
    suspend fun browseCategoryList() : Response<ApiResponse<CategoryListData>>
}