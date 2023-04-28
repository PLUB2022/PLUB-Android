package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRegisterHobbiesUseCase @Inject constructor(
    private val hobbyRepository: HobbyRepository
) {
    suspend fun invoke(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return hobbyRepository.registerInterest(request)
    }
}