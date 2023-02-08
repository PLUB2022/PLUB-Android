package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import kotlinx.coroutines.flow.Flow

interface ApplicantsRepository {
    suspend fun seeApplicants(request: Int) : Flow<UiState<HostApplicantsResponseVo>>
}