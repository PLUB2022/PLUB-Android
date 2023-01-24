package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    suspend fun nicknameCheck(request: String): Flow<UiState<Boolean>>
    suspend fun signUp(request: SignUpRequestVo): Flow<UiState<PlubJwtResponseVo>>
}