package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getEntireSchedule(request: GetEntireScheduleRequestVo): Flow<UiState<GetEntireScheduleResponseVo>>

    suspend fun putScheduleAttend(request: PutScheduleAttendRequestVo): Flow<UiState<CalendarAttendVo>>

    suspend fun createSchedule(request: CreateScheduleRequestVo): Flow<UiState<Unit>>
}