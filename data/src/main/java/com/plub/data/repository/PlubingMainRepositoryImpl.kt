package com.plub.data.repository

import com.plub.data.api.PlubingMainApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubingMainResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.repository.PlubingMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingMainRepositoryImpl @Inject constructor(private val plubingMainApi: PlubingMainApi) : PlubingMainRepository, BaseRepository() {

    override suspend fun getPlubingMain(id: Int): Flow<UiState<PlubingMainVo>> {
        return apiLaunch(plubingMainApi.getPlubingMain(id), PlubingMainResponseMapper)
    }
}