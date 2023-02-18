package com.plub.data.api

import com.plub.data.dto.media.UploadFilesResponse
import com.plub.data.base.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MediaApi {
    @Multipart
    @POST(Endpoints.FILE.FILE_URL)
    suspend fun uploadFile(@Part type: MultipartBody.Part, @Part files: MultipartBody.Part): Response<ApiResponse<UploadFilesResponse>>

    @Multipart
    @POST(Endpoints.FILE.CHANGE_FILE_URL)
    suspend fun changeFile(@Part type: MultipartBody.Part, @Part toDeleteUrls: MultipartBody.Part, @Part files: MultipartBody.Part): Response<ApiResponse<UploadFilesResponse>>
}