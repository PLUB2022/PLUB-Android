package com.plub.data.api

import com.plub.data.model.PlubJwtTokenResponse
import com.plub.data.model.ReIssueModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlubJwtTokenApi {
    @POST("/api/auth/reissue/")
    suspend fun reIssueToken(
        @Body refreshToken : ReIssueModel) : Response<PlubJwtTokenResponse>
}