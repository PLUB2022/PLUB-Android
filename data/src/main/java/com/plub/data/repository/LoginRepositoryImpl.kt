package com.plub.data.repository

import com.plub.data.api.LoginApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.SocialLoginRequestMapper
import com.plub.data.mapper.SocialLoginResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.LoginError
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository,
    BaseRepository() {

    override suspend fun socialLogin(request: SocialLoginRequestVo): Flow<UiState<SocialLoginResponseVo>> {
        val requestDto = SocialLoginRequestMapper.mapModelToDto(request)
        return apiLaunch(loginApi.socialLogin(requestDto), SocialLoginResponseMapper) {
            LoginError.make(it)
        }
    }
}