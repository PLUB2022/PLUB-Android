package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.LoginApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.SocialLoginRequestMapper
import com.plub.data.mapper.SocialLoginResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import com.plub.domain.result.LoginFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository, BaseRepository() {

    override fun socialLogin(request: SocialLoginRequestVo): Flow<UiState<SocialLoginResponseVo>> = flow {
        val requestDto = SocialLoginRequestMapper.mapModelToDto(request)
        apiLaunch(loginApi.socialLogin(requestDto), SocialLoginResponseMapper, object : UiStateCallback<SocialLoginResponseVo>() {
            override suspend fun onSuccess(state: UiState.Success<SocialLoginResponseVo>, customCode: Int) {
                val uiState = super.uiStateMapResult(state) {
                    LoginFailure.make(customCode)
                }
                emit(uiState)
            }

            override suspend fun onError(state: UiState.Error) {
                emit(state)
            }
        })
    }.onStart { emit(UiState.Loading) }.catch { e:Throwable ->
        e.printStackTrace()
        emit(UiState.Error(UiError.Invalided))
    }
}