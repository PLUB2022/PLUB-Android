package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInterestUseCase @Inject constructor(
    private val hobbyRepository: HobbyRepository,
) : UseCase<Unit, Flow<UiState<RegisterInterestResponseVo>>>(){
    override suspend fun invoke(request : Unit): Flow<UiState<RegisterInterestResponseVo>> {
        return hobbyRepository.browseInterest()
    }
}