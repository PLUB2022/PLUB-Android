package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.report.CreateReportRequestVo
import com.plub.domain.model.vo.report.ReportDetailVo
import com.plub.domain.model.vo.report.ReportVo
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun browseReport() : Flow<UiState<ReportVo>>
    suspend fun createReport(request : CreateReportRequestVo) : Flow<UiState<Unit>>
    suspend fun getReportDetail(request : Int) : Flow<UiState<ReportDetailVo>>
}