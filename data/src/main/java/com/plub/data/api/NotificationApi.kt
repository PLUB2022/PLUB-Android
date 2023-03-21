package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.notification.NotificationsResponse
import com.plub.data.dto.schedule.CalendarAttendResponse
import com.plub.data.dto.schedule.CreateScheduleRequest
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.data.dto.schedule.PutScheduleAttendRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationApi {

    companion object {
        const val NOTIFICATION_ID = "notificationId"
    }

    @GET(Endpoints.NOTIFICATIONS.MY)
    suspend fun getMyNotifications(): Response<ApiResponse<NotificationsResponse>>
}