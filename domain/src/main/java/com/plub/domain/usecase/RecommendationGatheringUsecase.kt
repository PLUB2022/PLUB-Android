package com.plub.domain.usecase

import RecommendationGatheringResponseVo
import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendationGatheringUsecase @Inject constructor(
    private val recommendationGatheringRepository: RecommendationGatheringRepository
) : UseCase<RecommendationGatheringRequestVo, Flow<UiState<RecommendationGatheringResponseVo>>>() {
    override suspend fun invoke(request: RecommendationGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>> {
        return recommendationGatheringRepository.getRecommendationGatheringList(request)
    }
}