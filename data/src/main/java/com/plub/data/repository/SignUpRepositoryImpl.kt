package com.plub.data.repository

import com.plub.data.api.SignUpApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.NicknameResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.NicknameError
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpApi: SignUpApi) : SignUpRepository, BaseRepository() {

    override suspend fun nicknameCheck(request: String): Flow<UiState<Boolean>> {
        return apiLaunch(signUpApi.nicknameCheck(request), NicknameResponseMapper) {
            NicknameError.make(it)
        }
    }
}