package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import kotlinx.coroutines.flow.Flow

interface RecruitApplyRepository {
    suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>>
}