package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.applicantsrecruitmapper.ApplicantsRecruitRequestMapper
import com.plub.data.mapper.applicantsrecruitmapper.ApplicantsRecruitResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.repository.ApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplicantsRecruitResposImpl @Inject constructor(private val recruitApi: RecruitApi) : ApplicantsRecruitRepository, BaseRepository() {
    override suspend fun applicantsRecruit(request: ApplicantsRecruitRequestVo): Flow<UiState<ApplicantsRecruitResponseVo>> {
        val requestDto = ApplicantsRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(recruitApi.applicantsRecruit(request.plubbingId, request.accessToken,requestDto), ApplicantsRecruitResponseMapper)
    }
}