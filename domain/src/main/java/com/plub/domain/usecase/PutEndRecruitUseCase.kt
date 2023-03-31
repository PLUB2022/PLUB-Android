package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEndRecruitUseCase @Inject constructor(private val recruitRepository: RecruitRepository
) : UseCase<Int, Flow<UiState<ApplicantsRecruitResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return recruitRepository.endRecruit(request)
    }
}