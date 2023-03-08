package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.data.dto.schedule.GetEntireScheduleResponse
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {

    @GET(Endpoints.PLUBBING.SCHEDULE.CALENDAR)
    suspend fun getEntireSchedule(@Path("plubbingId") plubbingId:Int, @Query("cursorId") cursorId:Int): Response<ApiResponse<GetEntireScheduleResponse>>
}