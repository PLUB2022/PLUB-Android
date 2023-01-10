package com.plub.data.api

import com.plub.data.model.SampleHomePostRequest
import com.plub.data.model.SampleHomePostResponse
import com.plub.data.model.categorylistdata.CategoryListData
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface BrowseApi {
    @GET("/api/plubbings/recommendation")
    suspend fun browseRecommendationGathering(
        @Query("offset") offset : Int,
        @Query("pageNumber") pageNumber : Int,
        @Query("pageSize") pageSize : Int,
        @Query("paged") paged : Boolean,
        @Query("sort.sorted") sort_sorted : Boolean,
        @Query("sort.unsorted") sort_unsorted : Boolean,
        @Query("unpaged") unpaged : Boolean
    ) : Response<ApiResponse<SampleHomePostResponse>>

    @GET("/api/categories")
    suspend fun browseCategoryList() : Response<ApiResponse<CategoryListData>>
}