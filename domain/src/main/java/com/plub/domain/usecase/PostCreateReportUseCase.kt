package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.report.CreateReportRequestVo
import com.plub.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCreateReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): UseCase<CreateReportRequestVo, Flow<UiState<Unit>>>() {

    override suspend operator fun invoke(request: CreateReportRequestVo): Flow<UiState<Unit>> {
        return reportRepository.createReport(request)
    }
}