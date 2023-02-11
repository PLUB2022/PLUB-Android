package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.repository.RegisterHobbiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRegisterHobbiesUseCase @Inject constructor(
    private val interestRepository: RegisterHobbiesRepository
) {
    suspend fun invoke(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return interestRepository.registerInterest(request)
    }
}