package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.applyrecruitmapper.QuestionsRecruitMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.repository.RecruitApplyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecruitApplyReposImpl @Inject constructor(private val recruitApi: RecruitApi): RecruitApplyRepository, BaseRepository() {
    override suspend fun getQuestions(request: Int): Flow<UiState<QuestionsResponseVo>> {
        return apiLaunch(recruitApi.getQustions(request), QuestionsRecruitMapper)
    }
}