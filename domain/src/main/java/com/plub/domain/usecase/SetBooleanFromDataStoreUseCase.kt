package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.datastore.DataStoreBooleanVo
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetBooleanFromDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: PrefDataStoreRepository
): UseCase<DataStoreBooleanVo, Flow<UiState<Nothing>>>() {
    override operator fun invoke(request: DataStoreBooleanVo): Flow<UiState<Nothing>> {
        return dataStoreRepository.setBoolean(request.key, request.value)
    }
}