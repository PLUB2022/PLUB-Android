package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.report.ReportDetailResponse
import com.plub.data.dto.report.ReportListResponse
import com.plub.domain.model.vo.report.CreateReportRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportApi {

    companion object{
        const val PATH_REPORT_ID = "reportId"
    }

    @GET(Endpoints.REPORT.BROWSE_REPORT)
    suspend fun browseReport(): Response<ApiResponse<ReportListResponse>>

    @POST(Endpoints.REPORT.CREATE_REPORT)
    suspend fun createReport(
        @Body request : CreateReportRequestVo
    ) : Response<ApiResponse<DataDto.DTO>>

    @GET(Endpoints.REPORT.GET_REPORT_DETAIL)
    suspend fun getReportDetail(
        @Path(PATH_REPORT_ID) reportId : Int
    ) : Response<ApiResponse<ReportDetailResponse>>
}