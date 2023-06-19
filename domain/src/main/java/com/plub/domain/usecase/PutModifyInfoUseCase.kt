package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyInfoRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.repository.GatheringRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PutModifyInfoUseCase @Inject constructor(
    private val gatheringRepository: GatheringRepository
):UseCase<ModifyInfoRequestVo, Flow<UiState<CreateGatheringResponseVo>>>() {

    override suspend operator fun invoke(request: ModifyInfoRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        return gatheringRepository.modifyInfo(request)
    }
}