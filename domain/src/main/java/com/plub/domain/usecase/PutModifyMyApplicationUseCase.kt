package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutModifyMyApplicationUseCase @Inject constructor(private val applicantsRepository: ApplicantsRepository)
    : UseCase<ApplicantsRecruitRequestVo, Flow<UiState<Unit>>>() {
    override suspend fun invoke(request: ApplicantsRecruitRequestVo): Flow<UiState<Unit>> {
        return applicantsRepository.modifyMyApplication(request)
    }
}