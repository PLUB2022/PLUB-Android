package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import kotlinx.coroutines.flow.Flow

interface ApplicantsRecruitRepository {
    suspend fun applicantsRecruit(request : ApplicantsRecruitRequestVo) : Flow<UiState<ApplicantsRecruitResponseVo>>
}