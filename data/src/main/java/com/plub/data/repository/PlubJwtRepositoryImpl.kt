package com.plub.data.repository

import androidx.datastore.core.DataStore
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubJwtReissueRequestMapper
import com.plub.data.mapper.PlubJwtResponseMapper
import com.plub.data.util.PlubJwtToken
import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.jwt.SavePlubJwtRequestVo
import com.plub.domain.repository.PlubJwtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlubJwtRepositoryImpl @Inject constructor(
    private val encryptedDataStore: DataStore<PlubJwtToken>,
    private val plubJwtTokenApi: PlubJwtTokenApi
) : PlubJwtRepository, BaseRepository() {

    override fun saveAccessTokenAndRefreshToken(request: SavePlubJwtRequestVo): Flow<Boolean> = flow {
        request.run {
            dataStoreLaunch(encryptedDataStore) {
                it.toBuilder().setAccessToken(accessToken).setRefreshToken(refreshToken).build()
            }
            emit(true)
        }
    }.catch { emit(false) }

    override fun getAccessToken(): Flow<String> = flow {
        emit(dataStoreLaunch(encryptedDataStore)?.accessToken ?: "")
    }

    override fun getRefreshToken(): Flow<String> = flow {
        emit(dataStoreLaunch(encryptedDataStore)?.refreshToken ?: "")
    }

    override suspend fun reIssueToken(request: PlubJwtReIssueRequestVo): Flow<UiState<PlubJwtResponseVo>> {
        val requestDto = PlubJwtReissueRequestMapper.mapModelToDto(request)
        return apiLaunch(plubJwtTokenApi.reIssueToken(requestDto),PlubJwtResponseMapper)
    }
}