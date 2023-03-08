package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.board.BoardCommentResponse
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingBoardResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.data.dto.board.BoardWriteRequest
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.data.dto.board.CommentEditRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlubingBoardApi {

    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_FEED_ID = "feedId"
        private const val PATH_COMMENT_ID = "commentId"
        private const val QUERY_CURSOR_ID = "cursorId"
    }

    @GET(Endpoints.PLUBBING.BOARD.PINS)
    suspend fun getPins(@Path(PATH_PLUBING_ID) plubbingId: Int): Response<ApiResponse<PlubingPinListResponse>>

    @GET(Endpoints.PLUBBING.BOARD.FEEDS)
    suspend fun getFeeds(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Query(QUERY_CURSOR_ID) cursorId: Int
    ): Response<ApiResponse<PlubingBoardListResponse>>

    @POST(Endpoints.PLUBBING.BOARD.FEED_CREATE)
    suspend fun createFeed(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Body boardWriteRequest: BoardWriteRequest
    ): Response<ApiResponse<DataDto.DTO>>

    @GET(Endpoints.PLUBBING.BOARD.FEED_DETAIL)
    suspend fun detailFeed(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int
    ): Response<ApiResponse<PlubingBoardResponse>>

    @PUT(Endpoints.PLUBBING.BOARD.FEED_CHANGE_PIN)
    suspend fun changePin(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @DELETE(Endpoints.PLUBBING.BOARD.FEED_DELETE)
    suspend fun deleteFeed(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.BOARD.FEED_EDIT)
    suspend fun editFeed(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int,
        @Body request: BoardWriteRequest
    ): Response<ApiResponse<PlubingBoardResponse>>

    @GET(Endpoints.PLUBBING.BOARD.COMMENTS)
    suspend fun getComments(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int,
        @Query(QUERY_CURSOR_ID) cursorId: Int
    ): Response<ApiResponse<BoardCommentListResponse>>

    @POST(Endpoints.PLUBBING.BOARD.COMMENT_CREATE)
    suspend fun createComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int,
        @Body request: CommentCreateRequest
    ): Response<ApiResponse<BoardCommentResponse>>

    @DELETE(Endpoints.PLUBBING.BOARD.COMMENT_DELETE)
    suspend fun deleteComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int,
        @Path(PATH_COMMENT_ID) commentId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.BOARD.COMMENT_EDIT)
    suspend fun editComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_FEED_ID) feedId: Int,
        @Path(PATH_COMMENT_ID) commentId: Int,
        @Body request: CommentEditRequest
    ): Response<ApiResponse<BoardCommentResponse>>
}