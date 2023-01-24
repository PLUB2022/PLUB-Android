package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>>
}