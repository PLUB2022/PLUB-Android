package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.notice.AppNoticeListResponse
import com.plub.data.dto.notice.NoticeWriteRequest
import com.plub.data.dto.notice.PlubingNoticeListResponse
import com.plub.data.dto.notice.PlubingNoticeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoticeApi {
    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_NOTICE_ID = "noticeId"
    }

    @GET(Endpoints.NOTICE.NOTICE_URL)
    suspend fun getAppNotice(): Response<ApiResponse<AppNoticeListResponse>>

    @GET(Endpoints.PLUBBING.NOTICE.NOTICE)
    suspend fun getPlubingNotice(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
    ): Response<ApiResponse<PlubingNoticeListResponse>>

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
}