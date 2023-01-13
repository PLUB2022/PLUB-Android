package com.plub.data.api

import com.plub.data.dto.plubJwt.SampleHomePostRequest
import com.plub.data.dto.plubJwt.SampleHomePostResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostHomeApi {
    @POST("/api/test/")
    suspend fun postHome(
        @Body testResultt : SampleHomePostRequest
    ) : Response<ApiResponse<SampleHomePostResponse>>
}