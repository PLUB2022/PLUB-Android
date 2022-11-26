package com.plub.data.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.plub.data.UiStateCallback
import com.plub.data.util.ApiResponse
import com.plub.domain.error.UiError
import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.Failure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <D : DataDto, M : DomainModel> request(
        response: Response<ApiResponse<D>>,
        responseMapper: Mapper.ResponseMapper<D, M>,
        result: UiStateCallback<M>
    ) {
        when (response.isSuccessful) {
            true -> {
                val apiResponse = response.body() as ApiResponse
                val statusCode = apiResponse.statusCode
                val data = responseMapper.mapDtoToModel(apiResponse.data)

                val stateResult: StateResult = when (statusCode == StateResult.SUCCEED_CODE) {
                    true -> StateResult.Succeed
                    false -> Failure.identifyFailure(statusCode)
                }

                result.onSuccess(UiState.Success(data, stateResult), statusCode)
            }
            false -> {
                val apiResponse = Gson().fromJson(response.errorBody()?.string(), ApiResponse::class.java)
                val error = UiError.identifyHttpError(response.code(), apiResponse.statusCode)
                result.onError(UiState.Error(error))
            }
        }
    }

    fun <T> request(dataStore: DataStore<Preferences>, key: Preferences.Key<T>): Flow<UiState<T?>> =
        dataStore.data
            .onStart { UiState.Loading }
            .catch { UiState.Error(UiError.Invalided) }
            .map { preferences -> UiState.Success(preferences[key], StateResult.Succeed) }

    fun <T> request(
        dataStore: DataStore<Preferences>, key: Preferences.Key<T>, value: T
    ): Flow<UiState<Nothing>> = flow {
        emit(UiState.Loading)
        try {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        } catch (e: Exception) {
            emit(UiState.Error(UiError.Invalided))
        }
    }

    suspend fun <T> request(
        dataStore: DataStore<T>
    ): T? {
        return dataStore.data.firstOrNull()
    }
}