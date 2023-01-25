package com.plub.domain.repository

import RecommendationGatheringResponseVo
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import kotlinx.coroutines.flow.Flow

interface CategoriesGatheringRepository {
    fun getCategoriesGatheringList(request : CategoriesGatheringRequestVo) : Flow<UiState<RecommendationGatheringResponseVo>>
}