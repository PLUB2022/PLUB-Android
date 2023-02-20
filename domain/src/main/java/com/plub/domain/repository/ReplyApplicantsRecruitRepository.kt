package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitResponseVo
import kotlinx.coroutines.flow.Flow

interface ReplyApplicantsRecruitRepository {
    suspend fun postApprovalApplicants(requestVo : ReplyApplicantsRecruitRequestVo) : Flow<UiState<ReplyApplicantsRecruitResponseVo>>
    suspend fun postRefuseApplicants(requestVo : ReplyApplicantsRecruitRequestVo) : Flow<UiState<ReplyApplicantsRecruitResponseVo>>
}