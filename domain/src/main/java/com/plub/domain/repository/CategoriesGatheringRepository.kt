package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesgatheringvo.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import kotlinx.coroutines.flow.Flow

interface CategoriesGatheringRepository {
    suspend fun getCategoriesGatheringList(request : CategoriesGatheringRequestVo) : Flow<UiState<RecommendationGatheringResponseVo>>
}