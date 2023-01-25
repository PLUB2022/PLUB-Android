package com.plub.domain.repository

import RecommendationGatheringResponseVo
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import kotlinx.coroutines.flow.Flow

interface RecommendationGatheringRepository {
    suspend fun getRecommendationGatheringList(request: RecommendationGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>>
}