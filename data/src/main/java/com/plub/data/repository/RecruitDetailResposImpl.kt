package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringRequestMapper
import com.plub.data.mapper.recommendationgatheringmapper.RecommendationGatheringResponseMapper
import com.plub.data.mapper.recruitdetailmapper.RecruitDetailRequestMapper
import com.plub.data.mapper.recruitdetailmapper.RecruitDetailResponseMapper
import com.plub.data.model.recruitdetail.RecruitDetailRequest
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.repository.RecommendationGatheringRepository
import com.plub.domain.repository.RecruitDetailRepository
import com.plub.domain.result.LoginFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecruitDetailResposImpl @Inject constructor(private val browseApi: BrowseApi) : RecruitDetailRepository, BaseRepository() {
    override suspend fun getRecruitDetail(request: RecruitDetailRequestVo): Flow<UiState<RecruitDetailResponseVo>> {
        val requestDto = RecruitDetailRequestMapper.mapDtoToModel(request)
        return apiLaunch(
            browseApi.browseRecruitDetail(
                requestDto.plubbingId,
                requestDto.accessToken
            ), RecruitDetailResponseMapper)
    }
}