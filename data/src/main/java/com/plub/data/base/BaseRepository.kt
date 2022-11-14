package com.plub.data.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.plub.data.UiStateCallback
import com.plub.domain.error.UiError
import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.Failure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <D : DataDto, M : DomainModel> request(
        response: Response<D>,
        mapper: Mapper<D, M>,
        result: UiStateCallback<M>
    ) {
        try {
            when (response.isSuccessful) {
                true -> {
                    val dto = response.body() as D
                    val domainModel = responseMapToModel(mapper,dto)

                    val stateResult: StateResult =
                        when (dto.customCode == StateResult.SUCCEED_CODE) {
                            true -> StateResult.Succeed
                            false -> Failure.identifyFailure(dto.customCode)
                        }

                    result.onSuccess(UiState.Success(domainModel, stateResult), dto.customCode)
                }
                false -> {
                    val dto = Gson().fromJson(response.errorBody()?.string(), DataDto::class.java)
                    val error = UiError.identifyHttpError(response.code(), dto.customCode)
                    result.onError(UiState.Error(error))
                }
            }
        } catch (exception: Throwable) {
            result.onError(UiState.Error(UiError.ServiceUnavailable))
        }
    }

    fun <T> request(dataStore: DataStore<Preferences>, key: Preferences.Key<T>): Flow<UiState<T?>> =
        dataStore.data
            .onStart { UiState.Loading }
            .catch { UiState.Error(UiError.Invalided) }
            .map { preferences -> UiState.Success(preferences[key], StateResult.Succeed) }

    fun <T> request(
        dataStore: DataStore<Preferences>,
        key: Preferences.Key<T>,
        value: T
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

    protected fun<D : DataDto, M : DomainModel> requestMapToDto(mapper: Mapper<D, M>, model:M):D {
        return mapper.mapModelToDto(model)
    }

    protected fun<D : DataDto, M : DomainModel> responseMapToModel(mapper: Mapper<D, M>, dto:D):M {
        return mapper.mapDtoToModel(dto)
    }
}