package com.plub.data.api

import com.plub.data.dto.plubJwt.PlubJwtReIssueRequest
import com.plub.data.dto.plubJwt.PlubJwtResponse
import com.plub.data.base.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlubJwtTokenApi {
    @POST("/api/auth/reissue")
    suspend fun reIssueToken(
        @Body request : PlubJwtReIssueRequest
    ) : Response<ApiResponse<PlubJwtResponse>>
}