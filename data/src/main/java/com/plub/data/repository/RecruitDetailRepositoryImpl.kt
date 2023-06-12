package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.CreateGatheringResponseMapper
import com.plub.data.mapper.ModifyQuestionRequestMapper
import com.plub.data.mapper.applicantsRecruitMapper.ApplicantsRecruitRequestMapper
import com.plub.data.mapper.applicantsRecruitMapper.ApplicantsRecruitResponseMapper
import com.plub.data.mapper.applyRecruitMapper.QuestionsRecruitMapper
import com.plub.data.mapper.recruitDetailMapper.RecruitDetailResponseMapper
import com.plub.data.mapper.recruitDetailMapper.host.HostRecruitEndMapper
import com.plub.domain.UiState
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecruitDetailRepositoryImpl @Inject constructor(private val recruitApi: RecruitApi) : RecruitRepository, BaseRepository() {
    override suspend fun getRecruitDetail(request: Int): Flow<UiState<RecruitDetailResponseVo>> {
        return apiLaunch(
            apiCall = { recruitApi.fetchRecruitDetail(request) }, RecruitDetailResponseMapper
        ){
            GatheringError.make(it)
        }
    }

    override suspend fun applyRecruit(request: ApplicantsRecruitRequestVo): Flow<UiState<ApplicantsRecruitResponseVo>> {
        val requestDto = ApplicantsRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { recruitApi.applicantsRecruit(request.plubbingId,requestDto) }, ApplicantsRecruitResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>> {
        return apiLaunch(apiCall = { recruitApi.getQustions(request) }, QuestionsRecruitMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun endRecruit(request: Int): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return apiLaunch(apiCall = { recruitApi.endRecruit(request) }, HostRecruitEndMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun modifyQuestions(request: ModifyQuestionRequestVo) : Flow<UiState<CreateGatheringResponseVo>> {
        val requestDto = ModifyQuestionRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { recruitApi.modifyQuestions(requestDto.plubbingId, requestDto.body) }, CreateGatheringResponseMapper)
    }
}