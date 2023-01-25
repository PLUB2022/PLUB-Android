package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.applicantsrecruitmapper.approvalmapper.ApprovalApplicantsRecruitMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalResponseVo
import com.plub.domain.repository.ApprovalApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApprovalApplicantsRecruitResposImpl @Inject constructor(
    private val recruitApi: RecruitApi
) : ApprovalApplicantsRecruitRepository, BaseRepository(){
    override suspend fun postApprovalApplicants(requestVo: ApplicantsRecruitApprovalRequestVo): Flow<UiState<ApplicantsRecruitApprovalResponseVo>> {
        TODO("Not yet implemented")
        return apiLaunch(recruitApi.approvalApplicants(requestVo.plubbingId,requestVo.accountId,requestVo.accessToken), ApprovalApplicantsRecruitMapper)
    }

}