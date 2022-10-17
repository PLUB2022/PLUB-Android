package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import kotlinx.coroutines.flow.Flow

interface SampleAccountRepository {
    suspend fun checkNickname(nickname :String): Flow<UiState<SampleAccount>>
}