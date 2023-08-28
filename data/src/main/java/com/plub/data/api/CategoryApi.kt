package com.plub.data.api

import com.plub.data.dto.hobby.AllHobbiesResponse
import com.plub.data.base.ApiResponse
import com.plub.data.dto.categoryListData.CategoryListResponse
import com.plub.data.dto.hobby.CategoryImageResponse
import com.plub.data.dto.hobby.SubHobbiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {

    companion object{
        private const val PATH_CATEGORY_ID = "categoryId"
        private const val PATH_SUB_CATEGORY_ID = "subCategoryId"
    }

    @GET(Endpoints.CATEGORY.GET_ALL_CATEGORIES)
    suspend fun allCategories() : Response<ApiResponse<AllHobbiesResponse>>

    @GET(Endpoints.CATEGORY.GET_SUB_CATEGORIES)
    suspend fun subCategories(
        @Path(PATH_CATEGORY_ID) request : Int
    ) : Response<ApiResponse<SubHobbiesResponse>>

    @GET(Endpoints.CATEGORY.GET_PARENT_CATEGORIES)
    suspend fun fetchCategoryList() : Response<ApiResponse<CategoryListResponse>>

    @GET(Endpoints.CATEGORY.GET_DEFAULT_CATEGORIES_IMAGES)
    suspend fun getDefaultCategoryImage(
        @Path(PATH_CATEGORY_ID) categoryId : Int,
        @Path(PATH_SUB_CATEGORY_ID) subCategoryId : Int
    ) : Response<ApiResponse<CategoryImageResponse>>
}