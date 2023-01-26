package com.plub.data.repository

import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.categoriesgatheringrequestmapper.CategoriesGatheringRequestMapper
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.repository.CategoriesGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesGatheringReposImpl @Inject constructor(private val browseApi: BrowseApi) : CategoriesGatheringRepository, BaseRepository() {
    override suspend fun getCategoriesGatheringList(request: CategoriesGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>> {
        val requestDto = CategoriesGatheringRequestMapper.mapDtoToModel(request)
        return apiLaunch(browseApi.fetchCategoriesGathering(
            requestDto.categoryId,
            requestDto.pageNumber,), RecommendationGatheringResponseMapper)
    }
}