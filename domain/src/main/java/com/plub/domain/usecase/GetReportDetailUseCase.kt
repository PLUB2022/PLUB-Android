package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.report.ReportDetailVo
import com.plub.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportDetailUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): UseCase<Int, Flow<UiState<ReportDetailVo>>>() {
    override suspend fun invoke(request : Int): Flow<UiState<ReportDetailVo>> {
        return reportRepository.getReportDetail(request)
    }

}