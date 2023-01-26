package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.repository.RecruitApplyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    val recruitApplyRepository: RecruitApplyRepository
): UseCase<Int, Flow<UiState<QuestionsResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<QuestionsResponseVo>> {
        return recruitApplyRepository.getQuestions(request)
    }

}