package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.SignUpApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.NicknameResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.repository.SignUpRepository
import com.plub.domain.result.NicknameFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpApi: SignUpApi) : SignUpRepository, BaseRepository() {

    override fun nicknameCheck(request: String): Flow<UiState<Boolean>> = flow {
        apiLaunch(signUpApi.nicknameCheck(request), NicknameResponseMapper, object : UiStateCallback<Boolean>() {
            override suspend fun onSuccess(state: UiState.Success<Boolean>, customCode: Int) {
                val uiState = super.uiStateMapResult(state) {
                    NicknameFailure.make(customCode)
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