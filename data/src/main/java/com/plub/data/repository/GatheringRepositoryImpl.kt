package com.plub.data.repository

import com.plub.data.api.GatheringApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.AccountInfosResponseMapper
import com.plub.data.mapper.CreateGatheringRequestMapper
import com.plub.data.mapper.CreateGatheringResponseMapper
import com.plub.data.mapper.ModifyRecruitRequestMapper
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.myGathering.MyGatheringListMapper
import com.plub.domain.UiState
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.model.vo.myGathering.KickOutRequestVo
import com.plub.domain.model.vo.myGathering.MyGatheringListResponseVo
import com.plub.domain.repository.GatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GatheringRepositoryImpl @Inject constructor(private val gatheringApi: GatheringApi) : GatheringRepository, BaseRepository() {

    override suspend fun createGathering(request: CreateGatheringRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        val requestDto = CreateGatheringRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { gatheringApi.createGathering(requestDto) }, CreateGatheringResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun modifyGathering(request: ModifyRecruitRequestVo): Flow<UiState<CreateGatheringResponseVo>> {
        val requestDto = ModifyRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { gatheringApi.modifyRecruit(requestDto.plubbingId, requestDto.body) }, CreateGatheringResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getMyParticipatingGatherings(request: Unit): Flow<UiState<MyGatheringListResponseVo>> {
        return apiLaunch(apiCall = { gatheringApi.getMyParticipatingGatherings() }, MyGatheringListMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getMyHostingGatherings(request: Unit): Flow<UiState<MyGatheringListResponseVo>> {
        return apiLaunch(apiCall = { gatheringApi.getMyHostingGatherings() }, MyGatheringListMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun changeGatheringStatus(request: Int): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { gatheringApi.changeGatheringStatus(request) }, UnitResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun leaveGathering(request: Int): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { gatheringApi.leaveGathering(request) }, UnitResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getMembers(request: Int): Flow<UiState<List<AccountInfoVo>>> {
        return apiLaunch(apiCall = { gatheringApi.getMembers(request) }, AccountInfosResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun kickOutMember(request: KickOutRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { gatheringApi.kickOutMember(request.plubbingId, request.accountId) }, UnitResponseMapper){
            GatheringError.make(it)
        }
    }
}