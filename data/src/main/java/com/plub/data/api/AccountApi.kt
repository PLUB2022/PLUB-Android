package com.plub.data.api

import com.plub.data.dto.account.MyInfoResponse
import com.plub.data.base.ApiResponse
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {

    companion object{
        const val PATH_NICKNAME = "nickname"
    }

    @GET(Endpoints.ACCOUNT.FETCH_MY_INFO)
    suspend fun fetchMyInfo(): Response<ApiResponse<MyInfoResponse>>

    @POST(Endpoints.ACCOUNT.UPDATE_PROFILE)
    suspend fun updateMyInfo(
        @Body request : UpdateMyInfoRequestVo
    ) : Response<ApiResponse<MyInfoResponse>>

    @POST(Endpoints.ACCOUNT.FETCH_OTHER_INFO)
    suspend fun fetchOtherInfo(
        @Path(PATH_NICKNAME) nickname : String
    ) : Response<ApiResponse<MyInfoResponse>>
}