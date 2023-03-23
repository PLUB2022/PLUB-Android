package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.myPage.MyApplicationResponse
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.myPage.MyGatheringResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageApi {
    companion object{
        private const val QUERY_STATUS = "status"
        private const val PATH_PLUBBING_ID = "plubbingId"
    }

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_GATHERING)
    suspend fun getMyGathering(
        @Query(QUERY_STATUS) status: String
    ): Response<ApiResponse<MyGatheringResponse>>

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_APPLICATION)
    suspend fun getMyApplication(
        @Path(PATH_PLUBBING_ID) plubbingId: Int
    ): Response<ApiResponse<MyApplicationResponse>>

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_TODO)
    suspend fun getMyToDo(
        @Path("plubbingId") plubbingId : Int,
        @Query("cursorId") cursorId: Int
    ): Response<ApiResponse<MyGatheringResponse>>

    @GET(Endpoints.PLUBBING.MY_PAGE.BROWSE_MY_POST)
    suspend fun getMyPost(
        @Path("plubbingId") plubbingId : Int,
        @Query("cursorId") cursorId: Int
    ): Response<ApiResponse<PlubingBoardListResponse>>
}