package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): UseCase<Unit, Flow<UiState<ReportVo>>>() {
    override suspend fun invoke(request : Unit): Flow<UiState<ReportVo>> {
        return reportRepository.browseReport()
    }

}