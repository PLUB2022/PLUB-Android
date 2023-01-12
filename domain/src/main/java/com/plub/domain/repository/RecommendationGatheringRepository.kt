package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import kotlinx.coroutines.flow.Flow

interface RecommendationGatheringRepository {
    fun getRecommendationGatheringList(request : RecommendationGatheringRequestVo) : Flow<UiState<RecommendationGatheringResponseVo>>
}