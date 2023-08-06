package com.plub.data.api

import com.plub.data.dto.account.MyInfoResponse
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.account.SmsCertificationRequest
import com.plub.data.dto.account.SmsRequest
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Path

interface AccountApi {

    companion object{
        const val QUERY_PUSH_NOTIFICATION = "push-notification"
        const val PATH_NICKNAME = "nickname"
        const val QUERY_INACTIVE = "inactive"
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

    @GET(Endpoints.ACCOUNT.FETCH_OTHER_INFO)
    suspend fun fetchOtherInfo(
        @Path(PATH_NICKNAME) nickname : String
    ) : Response<ApiResponse<MyInfoResponse>>

    @GET(Endpoints.AUTH.LOGOUT)
    suspend fun logout() : Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.ACCOUNT.PUT_INACTIVE)
    suspend fun inactive(
        @Query(QUERY_INACTIVE) inactive : Boolean
    ) : Response<ApiResponse<DataDto.DTO>>

    @POST(Endpoints.ACCOUNT.POST_REVOKE)
    suspend fun revoke() : Response<ApiResponse<DataDto.DTO>>

    @POST(Endpoints.ACCOUNT.POST_SMS)
    suspend fun sendSms(
        @Body request : SmsRequest
    ) : Response<ApiResponse<DataDto.DTO>>

    @POST(Endpoints.ACCOUNT.POST_SMS_CERTIFICATION)
    suspend fun smsCertification(
        @Body request : SmsCertificationRequest
    ) : Response<ApiResponse<DataDto.DTO>>
}