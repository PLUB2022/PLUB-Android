package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringBodyRequestVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringParamsVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import kotlinx.coroutines.flow.Flow

interface RecommendationGatheringRepository {
    suspend fun getRecommendationGatheringList(request: Int): Flow<UiState<PlubCardListVo>>
    suspend fun getCategoriesGatheringList(request: CategoriesGatheringParamsVo, requestBody: CategoriesGatheringBodyRequestVo): Flow<UiState<PlubCardListVo>>
}