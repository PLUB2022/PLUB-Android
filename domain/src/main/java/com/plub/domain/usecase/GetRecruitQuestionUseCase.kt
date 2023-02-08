package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecruitQuestionUseCase @Inject constructor(
    val recruitRepository: RecruitRepository
): UseCase<Int, Flow<UiState<QuestionsResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<QuestionsResponseVo>> {
        return recruitRepository.getQuestions(request)
    }

}