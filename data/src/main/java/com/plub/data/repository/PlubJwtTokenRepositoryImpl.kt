package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.util.PlubJwtToken
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlubJwtTokenRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>
) : PlubJwtTokenRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).build() }
    }

    override suspend fun saveAccessTokenAndRefreshToken(accessToken: String, refreshToken: String) {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).setRefreshToken(refreshToken).build() }
    }

    override suspend fun getAccessToken(): String = encryptedDataStore.data.first().accessToken


    override suspend fun getRefreshToken(): String = encryptedDataStore.data.first().refreshToken
}