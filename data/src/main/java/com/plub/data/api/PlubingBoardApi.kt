package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingBoardResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.data.dto.board.PostBoardRequest
import com.plub.data.dto.board.PostCommentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlubingBoardApi {

    @GET(Endpoints.PLUBBING.BOARD.FEEDS)
    suspend fun getBoardList(@Path("plubbingId") plubbingId:Int, @Query("page") page:Int): Response<ApiResponse<PlubingBoardListResponse>>

    @GET(Endpoints.PLUBBING.BOARD.PINS)
    suspend fun getPinList(@Path("plubbingId") plubbingId:Int): Response<ApiResponse<PlubingPinListResponse>>

    @PUT(Endpoints.PLUBBING.BOARD.CHANGE_PIN)
    suspend fun changePin(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int): Response<ApiResponse<DataDto.DTO>>

    @DELETE(Endpoints.PLUBBING.BOARD.DELETE_FEED)
    suspend fun deletePlubing(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int): Response<ApiResponse<DataDto.DTO>>

    @POST(Endpoints.PLUBBING.BOARD.POST_FEED)
    suspend fun postBoard(@Path("plubbingId") plubbingId:Int, @Body postBoardRequest: PostBoardRequest): Response<ApiResponse<DataDto.DTO>>

    @GET(Endpoints.PLUBBING.BOARD.GET_DETAIL_FEED)
    suspend fun getDetailBoard(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int): Response<ApiResponse<PlubingBoardResponse>>

    @GET(Endpoints.PLUBBING.BOARD.GET_COMMENTS)
    suspend fun getCommentList(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int, @Query("page") page:Int): Response<ApiResponse<BoardCommentListResponse>>

    @POST(Endpoints.PLUBBING.BOARD.POST_COMMENT)
    suspend fun postComment(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int, @Body request: PostCommentRequest): Response<ApiResponse<DataDto.DTO>>

    @DELETE(Endpoints.PLUBBING.BOARD.DELETE_COMMENT)
    suspend fun deleteComment(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int, @Path("commentId") commentId:Int): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.BOARD.EDIT_COMMENT)
    suspend fun editComment(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int, @Path("commentId") commentId:Int): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.BOARD.EDIT_BOARD)
    suspend fun editBoard(@Path("plubbingId") plubbingId:Int, @Path("feedId") feedId:Int, @Body request: PostBoardRequest): Response<ApiResponse<DataDto.DTO>>
}