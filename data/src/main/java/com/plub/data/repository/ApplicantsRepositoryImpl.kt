package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.applicantsRecruitMapper.ApplicantsRecruitRequestMapper
import com.plub.data.mapper.applicantsRecruitMapper.replyMapper.ReplyApplicantsRecruitMapper
import com.plub.data.mapper.recruitDetailMapper.host.HostSeeApplicantsMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.HostApplicantsResponseVo
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplicantsRepositoryImpl @Inject constructor(private val recruitApi: RecruitApi) : ApplicantsRepository, BaseRepository() {
    override suspend fun seeApplicants(request: Int): Flow<UiState<HostApplicantsResponseVo>> {
        return apiLaunch(recruitApi.seeApplicants(request), HostSeeApplicantsMapper)
    }

    override suspend fun postApprovalApplicants(requestVo: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return apiLaunch(recruitApi.approvalApplicants(requestVo.plubbingId,requestVo.accountId), ReplyApplicantsRecruitMapper)
    }

    override suspend fun postRefuseApplicants(requestVo: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        return apiLaunch(recruitApi.refuseApplicants(requestVo.plubbingId,requestVo.accountId), ReplyApplicantsRecruitMapper)
    }

    override suspend fun deleteMyApplication(request: Int): Flow<UiState<Unit>> {
        return apiLaunch(recruitApi.deleteMyApplication(request), UnitResponseMapper)
    }

    override suspend fun modifyMyApplication(request: ApplicantsRecruitRequestVo): Flow<UiState<Unit>> {
        val requestVo = ApplicantsRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(recruitApi.modifyMyApplication(request.plubbingId, requestVo), UnitResponseMapper)
    }
}