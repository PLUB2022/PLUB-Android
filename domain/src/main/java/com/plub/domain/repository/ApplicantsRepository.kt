package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.HostApplicantsResponseVo
import kotlinx.coroutines.flow.Flow

interface ApplicantsRepository {
    suspend fun seeApplicants(request: Int) : Flow<UiState<HostApplicantsResponseVo>>
    suspend fun postApprovalApplicants(requestVo : ReplyApplicantsRecruitRequestVo) : Flow<UiState<ReplyApplicantsRecruitResponseVo>>
    suspend fun postRefuseApplicants(requestVo : ReplyApplicantsRecruitRequestVo) : Flow<UiState<ReplyApplicantsRecruitResponseVo>>
    suspend fun deleteMyApplication(request : Int) : Flow<UiState<Int>>
    suspend fun modifyMyApplication(request: ApplicantsRecruitRequestVo) : Flow<UiState<Unit>>
}