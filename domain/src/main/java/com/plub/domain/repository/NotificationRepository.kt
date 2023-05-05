package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.notification.NotificationsResponseVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.DeleteScheduleRequestVo
import com.plub.domain.model.vo.schedule.EditScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getMyNotifications(request: Unit): Flow<UiState<NotificationsResponseVo>>


    suspend fun readNotification(request: Int): Flow<UiState<Unit>>
}