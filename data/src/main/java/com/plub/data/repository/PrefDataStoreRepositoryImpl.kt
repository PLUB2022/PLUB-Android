package com.plub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.plub.data.base.BaseRepository
import com.plub.domain.UiState
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PrefDataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>
) : PrefDataStoreRepository, BaseRepository() {

    override suspend fun getString(key: String): UiState<String?> = request(dataStorePreference, stringPreferencesKey(key))

    override suspend fun getInt(key: String): Flow<Int?> =
        dataStorePreference.data.map { preferences -> preferences[intPreferencesKey(key)] }

    override suspend fun getBoolean(key: String): Flow<Boolean?> =
        dataStorePreference.data.map { preferences -> preferences[booleanPreferencesKey(key)] }

    override suspend fun setString(key: String, value: String) {
        dataStorePreference.edit { preferences ->
            val a = stringPreferencesKey(key)
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun setInt(key: String, value: Int) {
        dataStorePreference.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        dataStorePreference.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
}