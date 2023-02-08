package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostApplyRecruitUseCase@Inject constructor(
    private val recruitRepository: RecruitRepository
) : UseCase<ApplicantsRecruitRequestVo, Flow<UiState<ApplicantsRecruitResponseVo>>>() {
    override suspend fun invoke(request: ApplicantsRecruitRequestVo): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return recruitRepository.applyRecruit(request)
    }
}