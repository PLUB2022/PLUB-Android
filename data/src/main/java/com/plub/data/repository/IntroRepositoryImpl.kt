package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.IntroApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.SampleLoginMapper
import com.plub.domain.error.UiError
import com.plub.domain.UiState
import com.plub.domain.result.LoginFailure
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.repository.IntroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val introApi: IntroApi) : IntroRepository, BaseRepository() {
    override fun trySampleLogin(): Flow<UiState<SampleLogin>> = flow {
        emit(UiState.Loading)
        delay(1000L)
    }

//        request(introApi.trySampleLogin(),SampleLoginMapper,object : UiStateCallback<SampleLogin>() {
//            override suspend fun onSuccess(state: UiState.Success<SampleLogin>, customCode: Int) {
//                val uiState = super.uiStateMapResult(state) {
//                    LoginFailure.make(customCode)
//                }
//                emit(uiState)
//            }
//
//            override suspend fun onError(state: UiState.Error) {
//                emit(state)
//            }
//        })
   // }.catch { emit(UiState.Error(UiError.Invalided)) }
}