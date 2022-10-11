package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.IntroApi
import com.plub.data.mapper.SampleLoginMapper
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.UiState
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.LoginFailure
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.IntroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val introApi: IntroApi) : IntroRepository {
    override fun trySampleLogin(): Flow<UiState<SampleLogin>> = flow {
        emit(UiState.Loading)
        delay(1000L)

        SampleLoginMapper.mapFromEntity(SampleLoginResponse("Login!","Register!"),object : UiStateCallback<SampleLogin>() {
            override suspend fun onSuccess(state: UiState.Success<SampleLogin>, customCode: Int) {
                val uiState = super.uiStateMapResult(state) {
                    when (customCode) {
                        LoginFailure.INVALIDED_ACCOUNT_CODE -> LoginFailure.InvalidedAccount("잘못된 계정 정보")
                        else -> IndividualFailure.Invalided
                    }
                }
                emit(uiState)
            }
        })
    }.catch { emit(UiState.Error(it)) }
}