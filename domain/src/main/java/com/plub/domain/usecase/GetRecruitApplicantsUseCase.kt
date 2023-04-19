package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.recruitDetailVo.host.HostApplicantsResponseVo
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecruitApplicantsUseCase @Inject constructor(private val hostRecruitRepository: ApplicantsRepository
) : UseCase<Int, Flow<UiState<HostApplicantsResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<HostApplicantsResponseVo>> {
        return hostRecruitRepository.seeApplicants(request)
    }
}