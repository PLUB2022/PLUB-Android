package com.plub.data.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plub.data.util.ApiResponse
import com.plub.domain.UiState
import com.plub.domain.error.CommonError
import com.plub.domain.error.IndividualError
import com.plub.data.util.DataStoreUtil
import com.plub.domain.error.UiError
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    fun <D : DataDto, M> apiLaunch(
        response: Response<ApiResponse<D>>,
        responseMapper: Mapper.ResponseMapper<D, M>,
        individualError: (Int) -> IndividualError = { IndividualError.Undefined }
    ): Flow<UiState<M>> = flow {
        when (response.isSuccessful) {
            true -> {
                val apiResponse = response.body() as ApiResponse
                val data = responseMapper.mapDtoToModel(apiResponse.data)
                val uiState = UiState.Success(data)
                emit(uiState)
            }
            false -> {
                val typeToken = object : TypeToken<ApiResponse<D>>() {}.type
                val apiResponse: ApiResponse<D> = Gson().fromJson(response.errorBody()?.string(), typeToken)
                val data = responseMapper.mapDtoToModel(apiResponse.data)
                val error = UiError.identifyError(apiResponse.statusCode)
                val uiError = UiState.Error(data, error)
                val uiState = uiStateMapIndividual(uiError, apiResponse.statusCode, individualError)
                emit(uiState)
            }
        }
    }.onStart { emit(UiState.Loading) }.catch { e:Throwable ->
        e.printStackTrace()
        emit(UiState.Error(null,CommonError.ServiceUnavailable))
    }

    private fun <T> uiStateMapIndividual(
        uiState: UiState.Error<T>,
        statusCode:Int,
        individualError: (Int) -> IndividualError
    ): UiState<T> {
        return when (uiState.error) {
            is CommonError -> uiState
            is IndividualError -> {
                uiState.apply {
                    error = individualError.invoke(statusCode)
                }
            }
        }
    }

    fun <T> dataStoreLaunch(dataStore: DataStore<Preferences>, key: Preferences.Key<T>): Flow<UiState<T?>> = flow<UiState<T?>> {
            val data = DataStoreUtil.getPreferencesData(dataStore, key)
            emit(UiState.Success(data.first()))
        }.onStart { emit(UiState.Loading) }.catch { emit(UiState.Error(null,CommonError.ServiceUnavailable)) }

    fun <T> dataStoreLaunch(dataStore: DataStore<Preferences>, key: Preferences.Key<T>, value: T): Flow<UiState<Unit>> = flow<UiState<Unit>> {
        DataStoreUtil.savePreferencesData(dataStore, key, value)
        emit(UiState.Success(Unit))
    }.onStart { emit(UiState.Loading) }.catch { emit(UiState.Error(null,CommonError.ServiceUnavailable)) }

    suspend fun <T> dataStoreLaunch(dataStore: DataStore<T>): T? = DataStoreUtil.getProtoData(dataStore)

    suspend fun <T> dataStoreLaunch(dataStore: DataStore<T>, update: suspend (t: T) -> T) {
        DataStoreUtil.saveProtoData(dataStore, update)
    }
}