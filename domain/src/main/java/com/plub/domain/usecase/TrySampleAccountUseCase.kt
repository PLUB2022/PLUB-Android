package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleAuthInfo
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.SampleAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrySampleAccountUseCase @Inject constructor(private val sampleAccountRepository: SampleAccountRepository) {
    suspend operator fun invoke(nickname : String): Flow<UiState<SampleAccount>> {
        return sampleAccountRepository.checkNickname(nickname)
    }

    suspend fun getAuthInfo() : Flow<UiState<SampleAuthInfo>>{
        return sampleAccountRepository.getAuthInfo()
    }
}