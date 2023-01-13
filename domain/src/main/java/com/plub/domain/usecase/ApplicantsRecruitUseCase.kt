package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.repository.ApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplicantsRecruitUseCase@Inject constructor(
    private val applicantsRecruitRepository: ApplicantsRecruitRepository
) : UseCase<ApplicantsRecruitRequestVo, Flow<UiState<ApplicantsRecruitResponseVo>>>() {
    override suspend fun invoke(request: ApplicantsRecruitRequestVo): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return applicantsRecruitRepository.applicantsRecruit(request)
    }
}