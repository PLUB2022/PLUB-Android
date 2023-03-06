package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.repository.GatheringRepository
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PutModifyQuestionsUseCase @Inject constructor(
    private val recruitRepository: RecruitRepository
):UseCase<ModifyQuestionRequestVo, Flow<UiState<CreateGatheringResponseVo>>>() {

    override suspend operator fun invoke(request: ModifyQuestionRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        return recruitRepository.modifyQuestions(request)
    }
}