package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.repository.ReplyApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostApprovalApplicantsRecruitUseCase @Inject constructor(
    private val approvalApplicantsRecruitRepository: ReplyApplicantsRecruitRepository
) : UseCase<ReplyApplicantsRecruitRequestVo, Flow<UiState<ReplyApplicantsRecruitResponseVo>>>(){
    override suspend fun invoke(request: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return approvalApplicantsRecruitRepository.postApprovalApplicants(request)
    }
}