package com.plub.data.api

import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.modifyGathering.ModifyRecruitRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequestBody
import com.plub.data.dto.myGathering.MyGatheringsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GatheringApi {
    companion object{
        private const val PATH_PLUBING_ID = "plubbingId"
    }

    @POST(Endpoints.PLUBBING.CREATE)
    suspend fun createGathering(@Body request: CreateGatheringRequest): Response<ApiResponse<CreateGatheringResponse>>

    @PUT(Endpoints.PLUBBING.MODIFY_GATHERING_RECRUIT)
    suspend fun modifyRecruit(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Body request: ModifyRecruitRequestBody
    ): Response<ApiResponse<CreateGatheringResponse>>

    @GET(Endpoints.PLUBBING.MY)
    suspend fun getMyParticipatingGatherings(
        @Query("isHost") isHost: Boolean = false
    ): Response<ApiResponse<MyGatheringsResponse>>

    @GET(Endpoints.PLUBBING.MY)
    suspend fun getMyHostingGatherings(
        @Query("isHost") isHost: Boolean = true
    ): Response<ApiResponse<MyGatheringsResponse>>

    @PUT(Endpoints.PLUBBING.CHANGE_STATUS)
    suspend fun changeGatheringStatus(
        @Path(PATH_PLUBING_ID) plubbingId: Int
    ): Response<ApiResponse<DataDto.DTO>>
}