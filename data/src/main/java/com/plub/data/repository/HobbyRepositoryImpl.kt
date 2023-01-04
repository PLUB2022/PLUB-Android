package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.HobbyApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.AllHobbiesResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HobbyRepositoryImpl @Inject constructor(private val hobbyApi: HobbyApi) : HobbyRepository, BaseRepository() {

    override fun allHobbies(): Flow<UiState<List<HobbyVo>>> = flow {
        apiLaunch(hobbyApi.allCategories(),AllHobbiesResponseMapper,object: UiStateCallback<List<HobbyVo>>() {
            override suspend fun onSuccess(state: UiState.Success<List<HobbyVo>>, customCode: Int) {
                emit(state)
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
