package com.plub.domain.usecase

import com.plub.domain.repository.InterestRepository
import javax.inject.Inject

class InterestUseCase @Inject constructor(
    private val interestRepository: InterestRepository
) {
    suspend fun invoke(request: List<Int>): Unit {
        return interestRepository.registerInterest(request)
    }
}