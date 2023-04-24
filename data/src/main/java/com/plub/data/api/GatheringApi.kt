package com.plub.data.api

import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.account.AccountInfoResponse
import com.plub.data.dto.account.AccountInfosResponse
import com.plub.data.dto.modifyGathering.ModifyRecruitRequestBody
import com.plub.data.dto.myGathering.MyGatheringsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GatheringApi {
    companion object{
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_ACCOUNT_ID = "accountId"
        private const val IS_HOST = "isHost"
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
        @Query(IS_HOST) isHost: Boolean = false
    ): Response<ApiResponse<MyGatheringsResponse>>

    @GET(Endpoints.PLUBBING.MY)
    suspend fun getMyHostingGatherings(
        @Query(IS_HOST) isHost: Boolean = true
    ): Response<ApiResponse<MyGatheringsResponse>>

    @PUT(Endpoints.PLUBBING.CHANGE_STATUS)
    suspend fun changeGatheringStatus(
        @Path(PATH_PLUBING_ID) plubbingId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.LEAVE)
    suspend fun leaveGathering(
        @Path(PATH_PLUBING_ID) plubbingId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @GET(Endpoints.PLUBBING.MEMBER)
    suspend fun getMembers(
        @Path(PATH_PLUBING_ID) plubbingId: Int
    ): Response<ApiResponse<AccountInfosResponse>>

    @DELETE(Endpoints.PLUBBING.KICK_OUT)
    suspend fun kickOutMember(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_ACCOUNT_ID) accountId: Int
    ): Response<ApiResponse<DataDto.DTO>>
}