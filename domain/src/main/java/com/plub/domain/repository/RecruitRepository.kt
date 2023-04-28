package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import kotlinx.coroutines.flow.Flow

interface RecruitRepository {
    suspend fun getRecruitDetail(request : Int) : Flow<UiState<RecruitDetailResponseVo>>
    suspend fun applyRecruit(request : ApplicantsRecruitRequestVo) : Flow<UiState<ApplicantsRecruitResponseVo>>
    suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>>
    suspend fun endRecruit(request : Int) : Flow<UiState<ApplicantsRecruitResponseVo>>

    suspend fun modifyQuestions(request: ModifyQuestionRequestVo) : Flow<UiState<CreateGatheringResponseVo>>
}