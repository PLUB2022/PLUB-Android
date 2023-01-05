package com.plub.domain.repository

import com.plub.domain.UiState
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun nicknameCheck(request: String): Flow<UiState<Boolean>>
}