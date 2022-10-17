package com.plub.data.api

import com.plub.data.model.PlubJwtTokenModel
import com.plub.data.util.PlubJwtToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PlubJwtTokenApi {
    @POST("/api/auth/reissue/")
    suspend fun reIssueToken(
        @Body refreshToken : String) : Response<PlubJwtTokenModel>
}