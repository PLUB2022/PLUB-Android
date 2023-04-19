package com.plub.data.repository

import com.plub.data.api.ReportApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.reportMapper.ReportMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.report.CreateReportRequestVo
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val reportApi: ReportApi) : ReportRepository, BaseRepository() {
    override suspend fun browseReport(): Flow<UiState<ReportVo>> {
        return apiLaunch(reportApi.browseReport(), ReportMapper)
    }

    override suspend fun createReport(request : CreateReportRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(reportApi.createReport(request), UnitResponseMapper)
    }
}