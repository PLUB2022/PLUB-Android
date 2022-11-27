package com.plub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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

    override fun setBoolean(key: String, value: Boolean): Flow<UiState<Nothing>> =
        dataStoreLaunch(dataStorePreference, booleanPreferencesKey(key), value)
}