package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.myPage.MyGatheringResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageApi {
    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_GATHERING)
    suspend fun getMyGathering(
        @Query("status") status: String
    ): Response<ApiResponse<MyGatheringResponse>>

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_TODO)
    suspend fun getMyToDo(
        @Path("plubbingId") plubbingId : Int,
        @Query("cursorId") cursorId: Int
    ): Response<ApiResponse<MyGatheringResponse>>

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_POST)
    suspend fun getMyPost(
        @Path("plubbingId") plubbingId : Int,
        @Query("cursorId") cursorId: Int
    ): Response<ApiResponse<MyGatheringResponse>>
}