package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.datastore.DataStoreRequestVo
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: PrefDataStoreRepository
): UseCase<DataStoreRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: DataStoreRequestVo): Flow<UiState<Unit>> {
        return when(request.value) {
            is String -> dataStoreRepository.setString(request.key, request.value)
            is Boolean -> dataStoreRepository.setBoolean(request.key, request.value)
            is Int -> dataStoreRepository.setInt(request.key, request.value)
            else -> throw IllegalAccessException()
        }
    }
}