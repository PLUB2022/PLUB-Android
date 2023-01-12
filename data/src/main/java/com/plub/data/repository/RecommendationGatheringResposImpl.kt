package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringRequestMapper
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.repository.RecommendationGatheringRepository
import com.plub.domain.result.LoginFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecommendationGatheringResposImpl @Inject constructor(private val browseApi: BrowseApi) : RecommendationGatheringRepository, BaseRepository() {
    override suspend fun getRecommendationGatheringList(request: RecommendationGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>> {
        val requestDto = RecommendationGatheringRequestMapper.mapDtoToModel(request)
        return apiLaunch(browseApi.browseRecommendationGathering(requestDto.pageNum, requestDto.accessToken), RecommendationGatheringResponseMapper)
    }
}