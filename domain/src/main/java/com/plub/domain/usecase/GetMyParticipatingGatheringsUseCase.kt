package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import com.plub.domain.repository.GatheringRepository
import com.plub.domain.repository.RecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyParticipatingGatheringsUseCase@Inject constructor(
    private val gatheringRepository: GatheringRepository
) : UseCase<Unit, Flow<UiState<MyGatheringsResponseVo>>>() {
    override suspend fun invoke(request: Unit): Flow<UiState<MyGatheringsResponseVo>> {
        return gatheringRepository.getMyParticipatingGatherings(request)
    }
}