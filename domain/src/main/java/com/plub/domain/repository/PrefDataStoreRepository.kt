package com.plub.domain.repository

import com.plub.domain.UiState
import kotlinx.coroutines.flow.Flow

interface PrefDataStoreRepository {
    fun getBoolean(key: String): Flow<UiState<Boolean?>>
    fun setBoolean(key: String, value: Boolean): Flow<UiState<Nothing>>
}