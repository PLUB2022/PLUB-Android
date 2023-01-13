package com.plub.data.repository

import com.plub.data.api.GatheringApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.CreateGatheringRequestMapper
import com.plub.data.mapper.CreateGatheringResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.repository.GatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GatheringRepositoryImpl @Inject constructor(private val gatheringApi: GatheringApi) : GatheringRepository, BaseRepository() {

    override suspend fun createGathering(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        val requestDto = CreateGatheringRequestMapper.mapModelToDto(request)
        return apiLaunch(gatheringApi.createGathering(requestDto), CreateGatheringResponseMapper)
    }
}