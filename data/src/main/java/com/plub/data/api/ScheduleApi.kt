package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.schedule.CalendarAttendResponse
import com.plub.data.dto.schedule.CreateScheduleRequest
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.data.dto.schedule.PutScheduleAttendRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {

    @GET(Endpoints.PLUBBING.SCHEDULE.CALENDAR)
    suspend fun getEntireSchedule(@Path("plubbingId") plubbingId:Int, @Query("cursorId") cursorId:Int): Response<ApiResponse<GetEntireScheduleResponse>>

    @PUT(Endpoints.PLUBBING.SCHEDULE.ATTEND)
    suspend fun putScheduleAttend(@Path("plubbingId") plubbingId:Int, @Path("calendarId") calendarId:Int, @Body body: PutScheduleAttendRequest): Response<ApiResponse<CalendarAttendResponse>>

    @POST(Endpoints.PLUBBING.SCHEDULE.CALENDAR)
    suspend fun createSchedule(@Path("plubbingId") plubbingId: Int, @Body body: CreateScheduleRequest): Response<ApiResponse<DataDto.DTO>>
}