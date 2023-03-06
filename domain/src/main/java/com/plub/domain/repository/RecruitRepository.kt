package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface RecruitRepository {
    suspend fun getRecruitDetail(request : Int) : Flow<UiState<RecruitDetailResponseVo>>
    suspend fun applyRecruit(request : ApplicantsRecruitRequestVo) : Flow<UiState<ApplicantsRecruitResponseVo>>
    suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>>
    suspend fun endRecruit(request : Int) : Flow<UiState<ApplicantsRecruitResponseVo>>
}