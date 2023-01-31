package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendationGatheringUsecase @Inject constructor(
    private val recommendationGatheringRepository: RecommendationGatheringRepository
) : UseCase<Int, Flow<UiState<PlubCardListVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<PlubCardListVo>> {
        return recommendationGatheringRepository.getRecommendationGatheringList(request)
    }
}