package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.IntroRepository
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooleanFromDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: PrefDataStoreRepository
) {
    operator fun invoke(key: String): Flow<UiState<Boolean?>> {
        return dataStoreRepository.getBoolean(key)
    }
}