package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.report.ReportResponse
import com.plub.domain.model.vo.report.CreateReportRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReportApi {
    @GET(Endpoints.REPORT.BROWSE_REPORT)
    suspend fun browseReport(): Response<ApiResponse<ReportResponse>>

    @POST(Endpoints.REPORT.CREATE_REPORT)
    suspend fun createReport(
        @Body request : CreateReportRequestVo
    ) : Response<ApiResponse<DataDto.DTO>>
}