package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.UiStateCallback
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubJwtTokenMapper
import com.plub.data.util.PlubJwtToken
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenResponseVo
import com.plub.domain.model.vo.jwt_token.SavePlubJwtTokenRequestVo
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlubJwtTokenRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>,
    private val plubJwtTokenApi: PlubJwtTokenApi
) : PlubJwtTokenRepository, BaseRepository() {

    override fun saveAccessTokenAndRefreshToken(request: SavePlubJwtTokenRequestVo): Flow<Boolean> = flow {
        request.run {
            encryptedDataStore.updateData {
                it.toBuilder().setAccessToken(accessToken).setRefreshToken(refreshToken).build()
            }
            emit(true)
        }
    }.catch { emit(false) }

    override fun getAccessToken(): Flow<String> = flow {
        emit(encryptedDataStore.data.firstOrNull()?.accessToken ?: "")
    }


    override fun getRefreshToken(): Flow<String> = flow {
        emit(encryptedDataStore.data.firstOrNull()?.refreshToken ?: "")
    }

    override fun reIssueToken(refreshToken: String): Flow<UiState<PlubJwtTokenResponseVo>> = flow {
        request(plubJwtTokenApi.reIssueToken(refreshToken),PlubJwtTokenMapper, object : UiStateCallback<PlubJwtTokenResponseVo>() {
            override suspend fun onSuccess(state: UiState.Success<PlubJwtTokenResponseVo>, customCode: Int) {
                val uiState = super.uiStateMapResult(state)
                emit(uiState)
            }

            override suspend fun onError(state: UiState.Error) {
                emit(state)
            }

        })
    }.catch { e:Throwable ->
        e.printStackTrace()
        emit(UiState.Error(UiError.Invalided))
    }
}