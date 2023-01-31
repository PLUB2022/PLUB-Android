package com.plub.data.repository

import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendationGatheringRepositoryImpl @Inject constructor(private val browseApi: BrowseApi) : RecommendationGatheringRepository, BaseRepository() {
    override suspend fun getRecommendationGatheringList(request: Int): Flow<UiState<PlubCardListVo>> {
        return apiLaunch(browseApi.fetchRecommendationGathering(request), PlubCardListResponseMapper)
    }
}