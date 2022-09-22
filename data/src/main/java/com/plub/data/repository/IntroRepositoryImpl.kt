package com.plub.data.repository

import com.plub.data.api.IntroApi
import com.plub.data.mapper.Mapper.mapperToSampleLogin
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.IntroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val introApi: IntroApi) : IntroRepository {
    override fun trySampleLogin(): Flow<UiState<SampleLogin>> = flow<UiState<SampleLogin>> {
        emit(UiState.Success(mapperToSampleLogin(SampleLoginResponse("Login!","Register!"))))
    }.catch { emit(UiState.Error(it)) }
}