package com.plub.data.api

import com.plub.data.dto.account.MyInfoResponse
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AccountApi {

    companion object{
        const val QUERY_PUSH_NOTIFICATION = "push-notification"
    }

    @GET(Endpoints.ACCOUNT.FETCH_MY_INFO)
    suspend fun fetchMyInfo(): Response<ApiResponse<MyInfoResponse>>

    @POST(Endpoints.ACCOUNT.UPDATE_PROFILE)
    suspend fun updateMyInfo(
        @Body request : UpdateMyInfoRequestVo
    ) : Response<ApiResponse<MyInfoResponse>>

    @PUT(Endpoints.ACCOUNT.CHANGE_PUSH_NOTIFICATION)
    suspend fun changePushNotify(
        @Query(QUERY_PUSH_NOTIFICATION) pushNotification : Boolean
    ) : Response<ApiResponse<DataDto.DTO>>
}