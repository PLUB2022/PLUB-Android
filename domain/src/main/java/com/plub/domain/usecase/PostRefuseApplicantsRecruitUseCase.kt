package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRefuseApplicantsRecruitUseCase @Inject constructor(
    private val applicantsRecruitRepository: ApplicantsRepository
) : UseCase<ReplyApplicantsRecruitRequestVo, Flow<UiState<ReplyApplicantsRecruitResponseVo>>>(){
    override suspend fun invoke(request: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return applicantsRecruitRepository.postRefuseApplicants(request)
    }
}