package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import kotlinx.coroutines.flow.Flow

interface GatheringRepository {
    suspend fun createGathering(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>>

    suspend fun modifyGathering(request: ModifyRecruitRequestVo): Flow<UiState<CreateGatheringResponseVo>>

    suspend fun getMyParticipatingGatherings(request: Unit): Flow<UiState<MyGatheringsResponseVo>>

    suspend fun getMyHostingGatherings(request: Unit): Flow<UiState<MyGatheringsResponseVo>>
}