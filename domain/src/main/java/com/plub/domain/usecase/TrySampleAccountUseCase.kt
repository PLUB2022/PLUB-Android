package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.SampleAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrySampleAccountUseCase @Inject constructor(private val sampleAccountRepository: SampleAccountRepository) {
    operator fun invoke(): Flow<UiState<SampleAccount>> {
        return sampleAccountRepository.checkNickname()
    }
}