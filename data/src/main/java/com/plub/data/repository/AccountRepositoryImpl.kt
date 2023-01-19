package com.plub.data.repository

import com.plub.data.api.AccountApi
import com.plub.data.api.SignUpApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.MyInfoResponseMapper
import com.plub.data.mapper.NicknameResponseMapper
import com.plub.data.mapper.PlubJwtResponseMapper
import com.plub.data.mapper.SignUpRequestMapper
import com.plub.domain.UiState
import com.plub.domain.error.NicknameError
import com.plub.domain.error.SignUpError
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import com.plub.domain.repository.AccountRepository
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountApi: AccountApi) : AccountRepository, BaseRepository() {

    override suspend fun fetchMyInfo(): Flow<UiState<MyInfoResponseVo>> {
        return apiLaunch(accountApi.fetchMyInfo(), MyInfoResponseMapper)
    }
}