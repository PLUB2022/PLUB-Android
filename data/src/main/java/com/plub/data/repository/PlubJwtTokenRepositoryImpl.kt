package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.UiStateCallback
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubJwtTokenReissueRequestMapper
import com.plub.data.mapper.PlubJwtTokenResponseMapper
import com.plub.data.util.PlubJwtToken
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.jwt_token.JWTTokenReIssueRequestVo
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenResponseVo
import com.plub.domain.model.vo.jwt_token.SavePlubJwtTokenRequestVo
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
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

    override fun reIssueToken(request: JWTTokenReIssueRequestVo): Flow<UiState<PlubJwtTokenResponseVo>> = flow {
        val requestDto = PlubJwtTokenReissueRequestMapper.mapModelToDto(request)
        request(plubJwtTokenApi.reIssueToken(requestDto),PlubJwtTokenResponseMapper, object : UiStateCallback<PlubJwtTokenResponseVo>() {
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