package com.plub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.plub.data.base.BaseRepository
import com.plub.domain.UiState
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrefDataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>
) : PrefDataStoreRepository, BaseRepository() {
    override fun getBoolean(key: String): Flow<UiState<Boolean?>> =
        dataStoreLaunch(dataStorePreference, booleanPreferencesKey(key))

    override fun setBoolean(key: String, value: Boolean): Flow<UiState<Unit>> =
        dataStoreLaunch(dataStorePreference, booleanPreferencesKey(key), value)

    override fun getString(key: String): Flow<UiState<String?>> =
        dataStoreLaunch(dataStorePreference, stringPreferencesKey(key))

    override fun setString(key: String, value: String): Flow<UiState<Unit>> =
        dataStoreLaunch(dataStorePreference, stringPreferencesKey(key), value)

    override fun getInt(key: String): Flow<UiState<Int?>> =
        dataStoreLaunch(dataStorePreference, intPreferencesKey(key))

    override fun setInt(key: String, value: Int): Flow<UiState<Unit>> =
        dataStoreLaunch(dataStorePreference, intPreferencesKey(key), value)
}