package com.plub.data.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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

    suspend fun <E : DataEntity, D : DomainModel> request(
        response: Response<E>,
        mapper: Mapper<E, D>,
        result: UiStateCallback<D>
    ) {
        try {
            when (response.isSuccessful) {
                true -> {
                    val entity = response.body() as E
                    val domainModel = mapper.mapFromEntity(entity)

                    val stateResult: StateResult =
                        when (entity.customCode == StateResult.SUCCEED_CODE) {
                            true -> StateResult.Succeed
                            false -> Failure.identifyFailure(entity.customCode)
                        }

                    result.onSuccess(UiState.Success(domainModel, stateResult), entity.customCode)
                }
                false -> {
                    val entity =
                        Gson().fromJson(response.errorBody()?.string(), DataEntity::class.java)
                    val error = UiError.identifyHttpError(response.code(), entity.customCode)
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
}