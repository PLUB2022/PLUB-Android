package com.plub.data.repository

import android.util.Log
import com.plub.data.api.SampleApi
import com.plub.data.mapper.Mapper
import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.repository.SampleAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SampleAccountImpl @Inject constructor(private val sampleApi: SampleApi) :
    SampleAccountRepository {

    override suspend fun checkNickname(nickname: String): Flow<UiState<SampleAccount>> = flow{
        emit(UiState.Loading)
        val response = sampleApi.checkNickname(nickname)
        if(response.isSuccessful) {
            emit(UiState.Success(Mapper.mapperToSampleAccount(response.body()!!)))
        }
    }
}