package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import kotlinx.coroutines.flow.Flow

interface CategoriesGatheringRepository {
    suspend fun getCategoriesGatheringList(request: CategoriesGatheringRequestVo): Flow<UiState<PlubCardListVo>>
}