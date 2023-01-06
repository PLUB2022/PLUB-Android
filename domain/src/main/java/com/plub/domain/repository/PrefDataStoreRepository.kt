package com.plub.domain.repository

import com.plub.domain.UiState
import kotlinx.coroutines.flow.Flow

interface PrefDataStoreRepository {
    fun setBoolean(key: String, value: Boolean): Flow<UiState<Unit>>
    fun setString(key: String, value: String): Flow<UiState<Unit>>
    fun setInt(key: String, value: Int): Flow<UiState<Unit>>
    fun getBoolean(key: String): Flow<UiState<Boolean?>>
    fun getString(key: String): Flow<UiState<String?>>
    fun getInt(key: String): Flow<UiState<Int?>>
}