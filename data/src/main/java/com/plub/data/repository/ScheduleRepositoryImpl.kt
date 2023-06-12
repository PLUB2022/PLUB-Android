package com.plub.data.repository

import com.plub.data.api.ScheduleApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.schedule.CalendarAttendResponseMapper
import com.plub.data.mapper.schedule.CreateScheduleRequestMapper
import com.plub.data.mapper.schedule.EditScheduleRequestMapper
import com.plub.data.mapper.schedule.EntireScheduleResponseMapper
import com.plub.data.mapper.schedule.PutScheduleAttendRequestMapper
import com.plub.domain.UiState
import com.plub.domain.error.ScheduleError
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.DeleteScheduleRequestVo
import com.plub.domain.model.vo.schedule.EditScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleApi: ScheduleApi) : ScheduleRepository, BaseRepository() {

    override suspend fun getEntireSchedule(request: GetEntireScheduleRequestVo): Flow<UiState<GetEntireScheduleResponseVo>> {
        return apiLaunch(apiCall = { scheduleApi.getEntireSchedule(request.plubbingId, request.cursorId) }, EntireScheduleResponseMapper){
            ScheduleError.make(it)
        }
    }

    override suspend fun putScheduleAttend(request: PutScheduleAttendRequestVo): Flow<UiState<CalendarAttendVo>> {
        val body = PutScheduleAttendRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { scheduleApi.putScheduleAttend(request.plubbingId, request.calendarId, body) }, CalendarAttendResponseMapper){
            ScheduleError.make(it)
        }
    }

    override suspend fun createSchedule(request: CreateScheduleRequestVo): Flow<UiState<Unit>> {
        val body = CreateScheduleRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { scheduleApi.createSchedule(request.plubbingId, body) }, UnitResponseMapper){
            ScheduleError.make(it)
        }
    }

    override suspend fun editSchedule(request: EditScheduleRequestVo): Flow<UiState<Unit>> {
        val body = EditScheduleRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { scheduleApi.editSchedule(request.plubbingId, request.calendarId, body) }, UnitResponseMapper){
            ScheduleError.make(it)
        }
    }

    override suspend fun deleteSchedule(request: DeleteScheduleRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { scheduleApi.deleteSchedule(request.plubbingId, request.calendarId) }, UnitResponseMapper){
            ScheduleError.make(it)
        }
    }
}