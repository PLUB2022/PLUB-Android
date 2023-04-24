package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.myGathering.KickOutRequestVo
import com.plub.domain.repository.GatheringRepository
import com.plub.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteKickOutMemberUseCase @Inject constructor(
    private val gatheringRepository: GatheringRepository
) : UseCase<KickOutRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: KickOutRequestVo): Flow<UiState<Unit>> {
        return gatheringRepository.kickOutMember(request)
    }
}