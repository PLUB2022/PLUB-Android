package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.model.SampleAuthInfo
import kotlinx.coroutines.flow.Flow

interface SampleAccountRepository {
    suspend fun checkNickname(nickname :String): Flow<UiState<SampleAccount>>
    suspend fun getAuthInfo(): Flow<UiState<SampleAuthInfo>>
}