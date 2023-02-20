package com.plub.data.repository

import com.plub.data.api.HomeBrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesGatheringResponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendationGatheringRepositoryImpl @Inject constructor(private val browseApi: HomeBrowseApi) : RecommendationGatheringRepository, BaseRepository() {
    override suspend fun getRecommendationGatheringList(request: Int): Flow<UiState<PlubCardListVo>> {
        return apiLaunch(browseApi.fetchRecommendationGathering(request), PlubCardListResponseMapper)
    }

    override suspend fun getCategoriesGatheringList(request: CategoriesGatheringRequestVo): Flow<UiState<PlubCardListVo>> {
        return apiLaunch(browseApi.fetchCategoriesGathering(
            request.categoryId,
            request.sort,
            request.pageNumber), PlubCardListResponseMapper)
    }
}