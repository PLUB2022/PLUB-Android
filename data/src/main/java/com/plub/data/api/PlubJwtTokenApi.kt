package com.plub.data.api

import com.plub.data.model.PlubJwtTokenResponse
import com.plub.data.model.JWTTokenReIssueRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlubJwtTokenApi {
    @POST("/api/auth/reissue/")
    fun reIssueToken(
        @Body refreshToken : JWTTokenReIssueRequest) : Response<PlubJwtTokenResponse>
}