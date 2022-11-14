package com.plub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.plub.data.base.BaseRepository
import com.plub.domain.UiState
import com.plub.domain.repository.PrefDataStoreRepository
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class PrefDataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStore<Preferences>
) : PrefDataStoreRepository, BaseRepository() {
    override fun getBoolean(key: String): Flow<UiState<Boolean?>> =
        request(dataStorePreference, booleanPreferencesKey(key))

    override fun setBoolean(key: String, value: Boolean): Flow<UiState<Nothing>> =
        request(dataStorePreference, booleanPreferencesKey(key), value)
}