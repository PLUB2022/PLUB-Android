package com.plub.data.repository

import com.plub.data.api.ReportApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.report.ReportDetailResponseMapper
import com.plub.data.mapper.reportMapper.ReportMapper
import com.plub.domain.UiState
import com.plub.domain.error.ReportError
import com.plub.domain.model.vo.report.CreateReportRequestVo
import com.plub.domain.model.vo.report.ReportDetailVo
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val reportApi: ReportApi) : ReportRepository, BaseRepository() {
    override suspend fun browseReport(): Flow<UiState<ReportVo>> {
        return apiLaunch(apiCall = { reportApi.browseReport() }, ReportMapper){
            ReportError.make(it)
        }
    }

    override suspend fun createReport(request : CreateReportRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { reportApi.createReport(request) }, UnitResponseMapper){
            ReportError.make(it)
        }
    }

    override suspend fun getReportDetail(request: Int): Flow<UiState<ReportDetailVo>> {
        return apiLaunch(apiCall = { reportApi.getReportDetail(request) }, ReportDetailResponseMapper){
            ReportError.make(it)
        }
    }
}