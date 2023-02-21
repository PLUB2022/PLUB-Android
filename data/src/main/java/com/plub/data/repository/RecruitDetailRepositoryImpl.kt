package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.modifyGathering.ModifyQuestionRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequest
import com.plub.data.mapper.CreateGatheringResponseMapper
import com.plub.data.mapper.ModifyQuestionRequestMapper
import com.plub.data.mapper.applicantsrecruitmapper.ApplicantsRecruitRequestMapper
import com.plub.data.mapper.applicantsrecruitmapper.ApplicantsRecruitResponseMapper
import com.plub.data.mapper.applyrecruitmapper.QuestionsRecruitMapper
import com.plub.data.mapper.recruitdetailmapper.RecruitDetailResponseMapper
import com.plub.data.mapper.recruitdetailmapper.host.HostRecruitEndMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecruitDetailRepositoryImpl @Inject constructor(private val recruitApi: RecruitApi) : RecruitRepository, BaseRepository() {
    override suspend fun getRecruitDetail(request: Int): Flow<UiState<RecruitDetailResponseVo>> {
        return apiLaunch(
            recruitApi.fetchRecruitDetail(request), RecruitDetailResponseMapper)
    }

    override suspend fun applyRecruit(request: ApplicantsRecruitRequestVo): Flow<UiState<ApplicantsRecruitResponseVo>> {
        val requestDto = ApplicantsRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(recruitApi.applicantsRecruit(request.plubbingId,requestDto), ApplicantsRecruitResponseMapper)
    }

    override suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>> {
        return apiLaunch(recruitApi.getQustions(request), QuestionsRecruitMapper)
    }

    override suspend fun endRecruit(request: Int): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return apiLaunch(recruitApi.endRecruit(request), HostRecruitEndMapper)
    }

    override suspend fun modifyQuestions(request: ModifyQuestionRequestVo) : Flow<UiState<CreateGatheringResponseVo>> {
        val requestDto = ModifyQuestionRequestMapper.mapModelToDto(request)
        return apiLaunch(recruitApi.modifyQuestions(requestDto.plubbingId, requestDto.body), CreateGatheringResponseMapper)
    }
}