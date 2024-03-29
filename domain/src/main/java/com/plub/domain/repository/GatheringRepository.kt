package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyInfoRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.model.vo.myGathering.KickOutRequestVo
import com.plub.domain.model.vo.myGathering.MyGatheringListResponseVo
import kotlinx.coroutines.flow.Flow

interface GatheringRepository {
    suspend fun createGathering(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>>

    suspend fun modifyGathering(request: ModifyRecruitRequestVo): Flow<UiState<CreateGatheringResponseVo>>

    suspend fun getMyParticipatingGatherings(request: Unit): Flow<UiState<MyGatheringListResponseVo>>

    suspend fun getMyHostingGatherings(request: Unit): Flow<UiState<MyGatheringListResponseVo>>

    suspend fun changeGatheringStatus(request: Int): Flow<UiState<Unit>>

    suspend fun leaveGathering(request: Int): Flow<UiState<Unit>>

    suspend fun getMembers(request: Int): Flow<UiState<List<AccountInfoVo>>>

    suspend fun kickOutMember(request: KickOutRequestVo): Flow<UiState<Unit>>

    suspend fun modifyInfo(request: ModifyInfoRequestVo): Flow<UiState<CreateGatheringResponseVo>>

    suspend fun pullUpGathering(request: Int): Flow<UiState<CreateGatheringResponseVo>>
}