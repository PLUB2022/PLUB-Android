package com.plub.presentation.util

import com.plub.domain.UiState
import com.plub.domain.model.vo.datastore.DataStoreRequestVo
import com.plub.domain.usecase.SetDataStoreUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtil @Inject constructor(
    private val setDataStoreUseCase: SetDataStoreUseCase
) {
    companion object {
        private const val SIGN_UP_TOKEN_KEY = "SIGN_UP_TOKEN_KEY"
    }

    fun setSignUpToken(value: String): Flow<UiState<Unit>> {
        return setDataStoreUseCase(DataStoreRequestVo(SIGN_UP_TOKEN_KEY, value))
    }
}