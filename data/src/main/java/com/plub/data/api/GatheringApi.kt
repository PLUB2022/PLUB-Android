package com.plub.data.api

import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.data.base.ApiResponse
import com.plub.data.dto.modifyGathering.ModifyRecruitRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GatheringApi {
    @POST(Endpoints.PLUBBING.CREATE)
    suspend fun createGathering(@Body request: CreateGatheringRequest): Response<ApiResponse<CreateGatheringResponse>>

    @PUT(Endpoints.PLUBBING.MODIFY_GATHERING.RECRUIT)
    suspend fun modifyRecruit(
        @Path("plubbingId") plubbingId: Int,
        @Body request: ModifyRecruitRequestBody
    ): Response<ApiResponse<CreateGatheringResponse>>
}