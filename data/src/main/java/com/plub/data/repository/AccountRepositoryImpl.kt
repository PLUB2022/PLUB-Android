package com.plub.data.repository

import com.plub.data.api.AccountApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.MyInfoResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountApi: AccountApi) : AccountRepository, BaseRepository() {

    override suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(accountApi.fetchMyInfo(), MyInfoResponseMapper)
    }

    override suspend fun updateMyInfo(request : UpdateMyInfoRequestVo): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(accountApi.updateMyInfo(request), MyInfoResponseMapper)
    }
}