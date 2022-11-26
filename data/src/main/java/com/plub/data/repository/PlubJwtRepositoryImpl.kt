package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.UiStateCallback
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubJwtReissueRequestMapper
import com.plub.data.mapper.PlubJwtResponseMapper
import com.plub.data.util.PlubJwtToken
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.jwt_token.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt_token.PlubJwtResponseVo
import com.plub.domain.model.vo.jwt_token.SavePlubJwtRequestVo
import com.plub.domain.repository.PlubJwtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlubJwtRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>,
    private val plubJwtTokenApi: PlubJwtTokenApi
) : PlubJwtRepository, BaseRepository() {

    override fun saveAccessTokenAndRefreshToken(request: SavePlubJwtRequestVo): Flow<Boolean> = flow {
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

    override fun reIssueToken(request: PlubJwtReIssueRequestVo): Flow<UiState<PlubJwtResponseVo>> = flow {
        val requestDto = PlubJwtReissueRequestMapper.mapModelToDto(request)
        request(plubJwtTokenApi.reIssueToken(requestDto),PlubJwtResponseMapper, object : UiStateCallback<PlubJwtResponseVo>() {
            override suspend fun onSuccess(state: UiState.Success<PlubJwtResponseVo>, customCode: Int) {
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