package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.applicantsrecruitmapper.replymapper.ReplyApplicantsRecruitMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.repository.ReplyApplicantsRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReplyApplicantsRecruitResposImpl @Inject constructor(
    private val recruitApi: RecruitApi
) : ReplyApplicantsRecruitRepository, BaseRepository(){
    override suspend fun postApprovalApplicants(requestVo: ReplyApplicantsRecruitRequestVo): Flow<UiState<ReplyApplicantsRecruitResponseVo>> {
        TODO("Not yet implemented")
        return apiLaunch(recruitApi.approvalApplicants(requestVo.plubbingId,requestVo.accountId,requestVo.accessToken), ReplyApplicantsRecruitMapper)
    }

}