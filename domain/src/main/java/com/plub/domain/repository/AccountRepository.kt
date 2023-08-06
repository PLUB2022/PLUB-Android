package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.SmsCertificationRequestVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>>
    suspend fun updateMyInfo(request : UpdateMyInfoRequestVo) : Flow<UiState<MyInfoResponseVo>>
    suspend fun fetchOtherInfo(reqeust : String) : Flow<UiState<MyInfoResponseVo>>
    suspend fun changePushNotification(request : Boolean) : Flow<UiState<Unit>>
    suspend fun inactive(request : Boolean) : Flow<UiState<Unit>>
    suspend fun revoke() : Flow<UiState<Unit>>
    suspend fun sendSms(request : String) : Flow<UiState<Unit>>
    suspend fun smsCertification(request : SmsCertificationRequestVo) : Flow<UiState<Unit>>
}