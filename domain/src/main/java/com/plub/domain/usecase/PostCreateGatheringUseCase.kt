package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.repository.GatheringRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostCreateGatheringUseCase @Inject constructor(
    private val gatheringRepository: GatheringRepository
):UseCase<CreateGatheringRequestVo, Flow<UiState<CreateGatheringResponseVo>>>() {

    override suspend operator fun invoke(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        return gatheringRepository.createGathering(request)
    }
}