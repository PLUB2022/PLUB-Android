package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.repository.RegistInterestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistInterestUseCase @Inject constructor(
    private val interestRepository: RegistInterestRepository
) {
    suspend fun invoke(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return interestRepository.registerInterest(request)
    }
}