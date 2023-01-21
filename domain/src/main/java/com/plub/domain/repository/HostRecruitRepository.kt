package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import kotlinx.coroutines.flow.Flow

interface HostRecruitRepository {
    suspend fun endRecruit(request : Int) : Flow<UiState<ApplicantsRecruitResponseVo>>
    suspend fun seeApplicants(request: Int) : Flow<UiState<HostApplicantsResponseVo>>
}