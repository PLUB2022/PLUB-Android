package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.report.ReportResponse
import retrofit2.Response
import retrofit2.http.GET

interface ReportApi {
    @GET(Endpoints.REPORT.BROWSE_REPORT)
    suspend fun browseReport(): Response<ApiResponse<ReportResponse>>
}