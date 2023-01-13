package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalResponseVo
import kotlinx.coroutines.flow.Flow

interface ApprovalApplicantsRecruitRepository {
    suspend fun postApprovalApplicants(requestVo : ApplicantsRecruitApprovalRequestVo) : Flow<UiState<ApplicantsRecruitApprovalResponseVo>>
}