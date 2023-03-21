package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.applicantsRecruitMapper.replyMapper.ReplyApplicantsRecruitMapper
import com.plub.data.mapper.recruitDetailMapper.host.HostSeeApplicantsMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
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
}