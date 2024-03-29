package com.plub.data.repository

import com.plub.data.api.AccountApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.MyInfoResponseMapper
import com.plub.data.mapper.UnitResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountApi: AccountApi) : AccountRepository, BaseRepository() {

    override suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(apiCall = { accountApi.fetchMyInfo() }, MyInfoResponseMapper)
    }

    override suspend fun fetchOtherInfo(request : String): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(apiCall = { accountApi.fetchOtherInfo(request) }, MyInfoResponseMapper)
    }

    override suspend fun updateMyInfo(request : UpdateMyInfoRequestVo): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(apiCall = { accountApi.updateMyInfo(request) }, MyInfoResponseMapper)
    }

    override suspend fun changePushNotification(request: Boolean): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.changePushNotify(request) }, UnitResponseMapper)
    }

    override suspend fun inactive(request: Boolean): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.inactive(request) }, UnitResponseMapper)
    }

    override suspend fun revoke(): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.revoke() }, UnitResponseMapper)
    }
}