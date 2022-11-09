package com.plub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PrefDataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>
) : PrefDataStoreRepository {
    /**
     * Exception 발생 시 빈 문자열("")을 반환합니다.
     */
    override suspend fun getString(key: String): Result<String> =
        Result.runCatching {
            val flow = dataStorePreference.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[stringPreferencesKey(key)]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }


    /**
     * Exception 발생 시 -1을 반환합니다.
     */
    override suspend fun getInt(key: String): Result<Int> =
        Result.runCatching {
            val flow = dataStorePreference.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[intPreferencesKey(key)]
                }
            val value = flow.firstOrNull() ?: -1
            value
        }

    /**
     * Exception 발생 시 null을 반환합니다.
     */
    override suspend fun getBoolean(key: String): Result<Boolean?> =
        Result.runCatching {
            val flow = dataStorePreference.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[booleanPreferencesKey(key)]
                }
            val value = flow.firstOrNull()
            value
        }

    override suspend fun setString(key: String, value: String) {
        Result.runCatching {
            dataStorePreference.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun setInt(key: String, value: Int) {
        Result.runCatching {
            dataStorePreference.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        Result.runCatching {
            dataStorePreference.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
        }
    }
}