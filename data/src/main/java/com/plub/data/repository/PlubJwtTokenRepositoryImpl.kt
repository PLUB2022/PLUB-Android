package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.mapper.PlubJwtTokenMapper
import com.plub.data.model.JWTTokenReIssueRequest
import com.plub.data.util.ApiResponse
import com.plub.data.util.PlubJwtToken
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenData
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenVo
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber
import javax.inject.Inject

class PlubJwtTokenRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>,
    private val plubJwtTokenApi: PlubJwtTokenApi
) : PlubJwtTokenRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).build() }
    }

    override suspend fun saveAccessTokenAndRefreshToken(accessToken: String, refreshToken: String) {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).setRefreshToken(refreshToken).build() }
    }

    override suspend fun getAccessToken(): String = encryptedDataStore.data.firstOrNull()?.accessToken ?: ""


    override suspend fun getRefreshToken(): String = encryptedDataStore.data.firstOrNull()?.refreshToken ?: ""

    override suspend fun reIssueToken(refreshToken: String): PlubJwtTokenVo {
        val tokenResponse = plubJwtTokenApi.reIssueToken(JWTTokenReIssueRequest(refreshToken))
        return when {
            !tokenResponse.isSuccessful ->
                PlubJwtTokenVo(PlubJwtTokenData("", ""))
            else ->
                PlubJwtTokenMapper.mapDtoToModel((tokenResponse.body() as ApiResponse).data)
        }
    }
}