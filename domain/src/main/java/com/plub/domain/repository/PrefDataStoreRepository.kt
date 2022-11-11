package com.plub.domain.repository

import com.plub.domain.UiState
import kotlinx.coroutines.flow.Flow

interface PrefDataStoreRepository {
    suspend fun getString(key: String): UiState<String?>

    suspend fun getInt(key: String): Flow<Int?>

    suspend fun getBoolean(key: String): Flow<Boolean?>

    suspend fun setString(key: String, value: String)

    suspend fun setInt(key: String, value: Int)

    suspend fun setBoolean(key: String, value: Boolean)
}