package com.plub.data.repository

import android.util.Log
import com.plub.data.api.SampleApi
import com.plub.data.mapper.Mapper
import com.plub.data.model.SampleAccountResponse
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.repository.SampleAccountRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SampleAccountImpl @Inject constructor(private val sampleApi: SampleApi) : SampleAccountRepository {
    override fun checkNickname(): Flow<UiState<SampleAccount>> = flow{
        //TODO("Not yet implemented")
        Log.d("TAg", sampleApi.checkNickname("123").toString())
    }

}