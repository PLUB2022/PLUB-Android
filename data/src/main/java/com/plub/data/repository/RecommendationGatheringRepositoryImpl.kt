package com.plub.data.repository

import com.plub.data.api.HomeApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.data.mapper.categoryGatheringMapper.CategoryGatheringBodyRequestMapper
import com.plub.domain.UiState
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringBodyRequestVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringParamsVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendationGatheringRepositoryImpl @Inject constructor(private val homeApi: HomeApi) :
    RecommendationGatheringRepository, BaseRepository() {
    override suspend fun getRecommendationGatheringList(request: Int): Flow<UiState<PlubCardListVo>> {
        return apiLaunch(
            apiCall = { homeApi.fetchRecommendationGathering(request) },
            PlubCardListResponseMapper
        ) {
            GatheringError.make(it)
        }
    }

    override suspend fun getCategoriesGatheringList(
        request: CategoriesGatheringParamsVo,
        requestBody: CategoriesGatheringBodyRequestVo
    ): Flow<UiState<PlubCardListVo>> {
        val body = CategoryGatheringBodyRequestMapper.mapModelToDto(requestBody)
        return apiLaunch(apiCall = {
            homeApi.fetchCategoriesGathering(
                request.categoryId,
                request.sort,
                request.pageNumber,
                body
            )
        }, PlubCardListResponseMapper) { GatheringError.make(it) }
    }
}

