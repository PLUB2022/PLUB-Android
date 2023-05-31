package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.board.BoardCommentResponse
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.data.dto.board.CommentEditRequest
import com.plub.data.dto.notice.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeApi {
    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_NOTICE_ID = "noticeId"
        private const val PATH_COMMENT_ID = "commentId"
        private const val PATH_ANNOUNCEMENT_ID = "announcementId"
        private const val QUERY_CURSOR_ID = "cursorId"
    }

    @GET(Endpoints.NOTICE.NOTICE_URL)
    suspend fun getAppNotice(): Response<ApiResponse<AppNoticeListResponse>>

    @GET(Endpoints.NOTICE.NOTICE_DETAIL)
    suspend fun getAppNoticeDetail(
        @Path(PATH_ANNOUNCEMENT_ID) announcementId: Int,
    ): Response<ApiResponse<AppNoticeResponse>>

    @GET(Endpoints.PLUBBING.NOTICE.NOTICE)
    suspend fun getPlubingNotice(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
    ): Response<ApiResponse<PlubingNoticeDataResponse>>

    @POST(Endpoints.PLUBBING.NOTICE.NOTICE)
    suspend fun postPlubingCreateNotice(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Body request: NoticeWriteRequest
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.NOTICE.NOTICE_EDIT)
    suspend fun putPlubingEditNotice(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
        @Body request: NoticeWriteRequest
    ): Response<ApiResponse<PlubingNoticeResponse>>

    @GET(Endpoints.PLUBBING.NOTICE.NOTICE_DETAIL)
    suspend fun getNoticeDetail(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
    ): Response<ApiResponse<PlubingNoticeResponse>>

    @DELETE(Endpoints.PLUBBING.NOTICE.NOTICE_DELETE)
    suspend fun deleteNotice(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
    ): Response<ApiResponse<DataDto.DTO>>

    @POST(Endpoints.PLUBBING.NOTICE.COMMENT_CREATE)
    suspend fun createComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
        @Body request: CommentCreateRequest
    ): Response<ApiResponse<BoardCommentResponse>>

    @DELETE(Endpoints.PLUBBING.NOTICE.COMMENT_DELETE)
    suspend fun deleteComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_COMMENT_ID) commentId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.NOTICE.COMMENT_EDIT)
    suspend fun editComment(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
        @Path(PATH_COMMENT_ID) commentId: Int,
        @Body request: CommentEditRequest
    ): Response<ApiResponse<BoardCommentResponse>>

    @GET(Endpoints.PLUBBING.NOTICE.COMMENTS)
    suspend fun getComments(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_NOTICE_ID) noticeId: Int,
        @Query(QUERY_CURSOR_ID) cursorId: Int
    ): Response<ApiResponse<BoardCommentListResponse>>
}