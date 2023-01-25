package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalResponseVo
import com.plub.domain.repository.ApprovalApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApprovalApplicantsRecruitUseCase @Inject constructor(
    private val approvalApplicantsRecruitRepository: ApprovalApplicantsRecruitRepository
) : UseCase<ApplicantsRecruitApprovalRequestVo, Flow<UiState<ApplicantsRecruitApprovalResponseVo>>>(){
    override suspend fun invoke(request: ApplicantsRecruitApprovalRequestVo): Flow<UiState<ApplicantsRecruitApprovalResponseVo>> {
        return approvalApplicantsRecruitRepository.postApprovalApplicants(request)
    }

}