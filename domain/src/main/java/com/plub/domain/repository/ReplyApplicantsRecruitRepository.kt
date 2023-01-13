package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import kotlinx.coroutines.flow.Flow

interface ReplyApplicantsRecruitRepository {
    suspend fun postApprovalApplicants(requestVo : ReplyApplicantsRecruitRequestVo) : Flow<UiState<ReplyApplicantsRecruitResponseVo>>
}