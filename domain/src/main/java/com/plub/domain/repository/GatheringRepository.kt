package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import kotlinx.coroutines.flow.Flow

interface GatheringRepository {
    suspend fun createGathering(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>>
}