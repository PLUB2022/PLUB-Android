package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.repository.ReplyApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReplyApplicantsRecruitUseCase @Inject constructor(
    private val approvalApplicantsRecruitRepository: ReplyApplicantsRecruitRepository
) : UseCase<ReplyApplicantsRecruitRequestVo, Flow<UiState<ReplyApplicantsRecruitResponseVo>>>(){
    override suspend fun invoke(request: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return approvalApplicantsRecruitRepository.postApprovalApplicants(request)
    }

    suspend fun refuseInvoke(request: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return approvalApplicantsRecruitRepository.postRefuseApplicants(request)
    }

}