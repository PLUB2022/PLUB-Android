package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecruitDetailUseCase@Inject constructor(
    private val recruitDetailRepository: RecruitRepository
) : UseCase<Int, Flow<UiState<RecruitDetailResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<RecruitDetailResponseVo>> {
        return recruitDetailRepository.getRecruitDetail(request)
    }
}