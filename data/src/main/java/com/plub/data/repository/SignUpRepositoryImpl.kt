package com.plub.data.repository

import com.plub.data.api.SignUpApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.NicknameResponseMapper
import com.plub.data.mapper.PlubJwtResponseMapper
import com.plub.data.mapper.SignUpRequestMapper
import com.plub.domain.UiState
import com.plub.domain.error.NicknameError
import com.plub.domain.error.SignUpError
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpApi: SignUpApi) : SignUpRepository, BaseRepository() {

    override suspend fun nicknameCheck(request: String): Flow<UiState<Boolean>> {
        return apiLaunch(apiCall = { signUpApi.nicknameCheck(request) }, NicknameResponseMapper) {
            NicknameError.make(it)
        }
    }

    override suspend fun signUp(request: SignUpRequestVo): Flow<UiState<PlubJwtResponseVo>> {
        val requestDto = SignUpRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { signUpApi.signUp(requestDto) }, PlubJwtResponseMapper) {
            SignUpError.make(it)
        }
    }
}