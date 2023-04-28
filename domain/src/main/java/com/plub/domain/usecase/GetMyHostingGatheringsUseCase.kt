package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.myGathering.MyGatheringListResponseVo
import com.plub.domain.repository.GatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyHostingGatheringsUseCase@Inject constructor(
    private val gatheringRepository: GatheringRepository
) : UseCase<Unit, Flow<UiState<MyGatheringListResponseVo>>>() {
    override suspend fun invoke(request: Unit): Flow<UiState<MyGatheringListResponseVo>> {
        return gatheringRepository.getMyHostingGatherings(request)
    }
}