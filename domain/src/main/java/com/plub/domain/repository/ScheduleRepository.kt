package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getEntireSchedule(request: GetEntireScheduleRequestVo): Flow<UiState<GetEntireScheduleResponseVo>>
}