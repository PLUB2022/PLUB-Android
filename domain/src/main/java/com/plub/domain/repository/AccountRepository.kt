package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>>
    suspend fun updateMyInfo(request : UpdateMyInfoRequestVo) : Flow<UiState<MyInfoResponseVo>>
    suspend fun fetchOtherInfo(reqeust : String) : Flow<UiState<MyInfoResponseVo>>
}