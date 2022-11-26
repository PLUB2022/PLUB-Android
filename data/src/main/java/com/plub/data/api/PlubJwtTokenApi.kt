package com.plub.data.api

import com.plub.data.dto.sample.JWTTokenReIssueRequest
import com.plub.data.dto.sample.PlubJwtTokenResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlubJwtTokenApi {
    @POST("/api/auth/reissue/")
    suspend fun reIssueToken(
        @Body request : JWTTokenReIssueRequest
    ) : Response<ApiResponse<PlubJwtTokenResponse>>
}