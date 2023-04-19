package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
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

interface ScheduleApi {

    companion object {
        const val PLUBBING_ID = "plubbingId"
        const val CALENDAR_ID = "calendarId"
        const val CURSOR_ID = "cursorId"
    }

    @GET(Endpoints.PLUBBING.SCHEDULE.CALENDAR)
    suspend fun getEntireSchedule(@Path(PLUBBING_ID) plubbingId:Int, @Query(CURSOR_ID) cursorId:Int): Response<ApiResponse<GetEntireScheduleResponse>>

    @PUT(Endpoints.PLUBBING.SCHEDULE.ATTEND)
    suspend fun putScheduleAttend(@Path(PLUBBING_ID) plubbingId:Int, @Path(CALENDAR_ID) calendarId:Int, @Body body: PutScheduleAttendRequest): Response<ApiResponse<CalendarAttendResponse>>

    @POST(Endpoints.PLUBBING.SCHEDULE.CALENDAR)
    suspend fun createSchedule(@Path(PLUBBING_ID) plubbingId: Int, @Body body: CreateScheduleRequest): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.SCHEDULE.CALENDAR_ID)
    suspend fun editSchedule(@Path(PLUBBING_ID) plubbingId: Int, @Path(CALENDAR_ID) calendarId:Int, @Body body: CreateScheduleRequest): Response<ApiResponse<DataDto.DTO>>

    @DELETE(Endpoints.PLUBBING.SCHEDULE.CALENDAR_ID)
    suspend fun deleteSchedule(@Path(PLUBBING_ID) plubbingId:Int, @Path(CALENDAR_ID) calendarId:Int): Response<ApiResponse<DataDto.DTO>>
}