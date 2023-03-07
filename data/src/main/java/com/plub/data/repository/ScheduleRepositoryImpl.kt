package com.plub.data.repository

import com.plub.data.api.ScheduleApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.schedule.EntireScheduleResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) : ScheduleRepository, BaseRepository() {

    override suspend fun getEntireSchedule(request: GetEntireScheduleRequestVo): Flow<UiState<GetEntireScheduleResponseVo>> {
        return apiLaunch(scheduleApi.getEntireSchedule(request.plubbingId, request.cursorId), EntireScheduleResponseMapper)
    }
}