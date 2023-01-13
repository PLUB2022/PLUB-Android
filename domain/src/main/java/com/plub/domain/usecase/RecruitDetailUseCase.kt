package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.repository.RecruitDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecruitDetailUseCase@Inject constructor(
    private val recruitDetailRepository: RecruitDetailRepository
) : UseCase<RecruitDetailRequestVo, Flow<UiState<RecruitDetailResponseVo>>>() {
    override suspend fun invoke(request: RecruitDetailRequestVo): Flow<UiState<RecruitDetailResponseVo>> {
        return recruitDetailRepository.getRecruitDetail(request)
    }
}