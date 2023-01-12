package com.plub.data.api

import com.plub.data.dto.media.UploadFilesResponse
import com.plub.data.util.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MediaApi {
    @Multipart
    @POST(Endpoints.FILE.FILE_URL)
    suspend fun uploadFile(@Part type: MultipartBody.Part, @Part files: MultipartBody.Part): Response<ApiResponse<UploadFilesResponse>>
}