package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.categoriesgatheringrequestmapper.CategoriesGatheringRequestMapper
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.repository.CategoriesGatheringRepository
import com.plub.domain.result.LoginFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CategoriesGatheringResposImpl @Inject constructor(private val browseApi: BrowseApi) : CategoriesGatheringRepository, BaseRepository() {
    override suspend fun getCategoriesGatheringList(request: CategoriesGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>> {
        val requestDto = CategoriesGatheringRequestMapper.mapDtoToModel(request)
        return apiLaunch(browseApi.browseCategoriesGathering(
                requestDto.categoryId,
                requestDto.pageNumber,
                requestDto.accessToken), RecommendationGatheringResponseMapper)
    }
}