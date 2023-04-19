package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.report.ReportVo
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun browseReport() : Flow<UiState<ReportVo>>
}