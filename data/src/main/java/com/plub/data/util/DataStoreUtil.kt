package com.plub.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object DataStoreUtil {

    fun <T> getPreferencesData(
        dataStore: DataStore<Preferences>,
        key: Preferences.Key<T>
    ): Flow<T?> = dataStore.data.map { preferences -> preferences[key] }

    suspend fun <T> savePreferencesData(
        dataStore: DataStore<Preferences>,
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun <T> getProtoData(dataStore: DataStore<T>): T? = dataStore.data.firstOrNull()

    suspend fun <T> saveProtoData(dataStore: DataStore<T>, update: suspend (t: T) -> T) {
        dataStore.updateData(update)
    }
}