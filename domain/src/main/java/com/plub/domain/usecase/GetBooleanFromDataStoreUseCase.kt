package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooleanFromDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: PrefDataStoreRepository
): UseCase<String, Flow<UiState<Boolean?>>>() {
    override suspend operator fun invoke(request: String): Flow<UiState<Boolean?>> {
        return dataStoreRepository.getBoolean(request)
    }
}

