package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.repository.GatheringRepository
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGatheringMembersUseCase @Inject constructor(
    private val gatheringRepository: GatheringRepository,
) : UseCase<Int, Flow<UiState<List<AccountInfoVo>>>>(){
    override suspend fun invoke(request : Int): Flow<UiState<List<AccountInfoVo>>> {
        return gatheringRepository.getMembers(request)
    }
}