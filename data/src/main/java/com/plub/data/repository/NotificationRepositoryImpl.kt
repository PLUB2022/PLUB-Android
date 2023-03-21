package com.plub.data.repository

import com.plub.data.api.NotificationApi
import com.plub.data.api.ScheduleApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.NotificationsResponseMapper
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.schedule.CalendarAttendResponseMapper
import com.plub.data.mapper.schedule.CreateScheduleRequestMapper
import com.plub.data.mapper.schedule.EditScheduleRequestMapper
import com.plub.data.mapper.schedule.EntireScheduleResponseMapper
import com.plub.data.mapper.schedule.PutScheduleAttendRequestMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.notification.NotificationsResponseVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.DeleteScheduleRequestVo
import com.plub.domain.model.vo.schedule.EditScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import com.plub.domain.repository.NotificationRepository
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val notificationApi: NotificationApi) : NotificationRepository, BaseRepository() {

    override suspend fun getMyNotifications(request: Unit): Flow<UiState<NotificationsResponseVo>> {
        return apiLaunch(notificationApi.getMyNotifications(), NotificationsResponseMapper)
    }
}