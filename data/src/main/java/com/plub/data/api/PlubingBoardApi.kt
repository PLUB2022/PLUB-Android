package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.data.dto.plub.PlubingMainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlubingBoardApi {

    @GET(Endpoints.PLUBBING.BOARD.FEEDS)
    suspend fun getBoardList(@Path("plubbingId") plubbingId:Int, @Query("page") page:Int): Response<ApiResponse<PlubingBoardListResponse>>

    @GET(Endpoints.PLUBBING.BOARD.PINS)
    suspend fun getPinList(@Path("plubbingId") plubbingId:Int): Response<ApiResponse<PlubingPinListResponse>>
}