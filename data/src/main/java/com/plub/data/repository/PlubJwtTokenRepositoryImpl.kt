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
import kotlinx.coroutines.flow.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import timber.log.Timber
import javax.inject.Inject

class PlubJwtTokenRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>,
    private val plubJwtTokenApi: PlubJwtTokenApi
) : PlubJwtTokenRepository {
    override fun saveAccessToken(accessToken: String): Flow<Nothing> = flow {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).build() }
    }

    override fun saveAccessTokenAndRefreshToken(accessToken: String, refreshToken: String): Flow<Nothing> = flow {
        encryptedDataStore.updateData { it.toBuilder().setAccessToken(accessToken).setRefreshToken(refreshToken).build() }
    }

    override fun getAccessToken(): Flow<String> = flow {
        emit(encryptedDataStore.data.firstOrNull()?.accessToken ?: "")
    }


    override fun getRefreshToken(): Flow<String> = flow {
        emit(encryptedDataStore.data.firstOrNull()?.refreshToken ?: "")
    }

    override fun reIssueToken(refreshToken: String): Flow<PlubJwtTokenVo> = flow {
        val tokenResponse = plubJwtTokenApi.reIssueToken(JWTTokenReIssueRequest(refreshToken))
        when {
            !tokenResponse.isSuccessful ->
                emit(PlubJwtTokenVo(PlubJwtTokenData("", "")))
            else ->
                emit(PlubJwtTokenMapper.mapDtoToModel((tokenResponse.body() as ApiResponse).data))
        }
    }
}