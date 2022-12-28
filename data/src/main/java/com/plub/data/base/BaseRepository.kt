package com.plub.data.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.plub.data.UiStateCallback
import com.plub.data.util.ApiResponse
import com.plub.data.util.DataStoreUtil
import com.plub.domain.error.UiError
import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.Failure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <D : DataDto, M> apiLaunch(
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

    fun <T> dataStoreLaunch(dataStore: DataStore<Preferences>, key: Preferences.Key<T>): Flow<UiState<T?>> = flow<UiState<T?>> {
            val data = DataStoreUtil.getPreferencesData(dataStore, key)
            emit(UiState.Success(data.first(),StateResult.Succeed))
        }.onStart { emit(UiState.Loading) }.catch { emit(UiState.Error(UiError.Invalided)) }

    fun <T> dataStoreLaunch(dataStore: DataStore<Preferences>, key: Preferences.Key<T>, value: T): Flow<UiState<Nothing>> = flow<UiState<Nothing>> {
        DataStoreUtil.savePreferencesData(dataStore, key, value)
    }.onStart { emit(UiState.Loading) }.catch { emit(UiState.Error(UiError.Invalided)) }

    suspend fun <T> dataStoreLaunch(dataStore: DataStore<T>): T? = DataStoreUtil.getProtoData(dataStore)

    suspend fun <T> dataStoreLaunch(dataStore: DataStore<T>, update: suspend (t: T) -> T) {
        DataStoreUtil.saveProtoData(dataStore, update)
    }
}