package com.plub.data.api

import com.plub.data.dto.media.UploadFilesResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface SearchApi {
    @Multipart
    @POST(Endpoints.PLUBBING.SEARCH)
    suspend fun plubSearch(): Response<ApiResponse<UploadFilesResponse>>
}
