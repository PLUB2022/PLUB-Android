package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import kotlinx.coroutines.flow.Flow

interface RecommendationGatheringRepository {
    suspend fun getRecommendationGatheringList(request: RecommendationGatheringRequestVo): Flow<UiState<PlubCardListVo>>
}