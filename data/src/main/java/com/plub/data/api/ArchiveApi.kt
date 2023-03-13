package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.archive.ArchiveUpdateRequest
import com.plub.data.dto.archive.ArchiveUpdateResponse
import com.plub.data.dto.archive.ArchiveDetailResponse
import com.plub.data.dto.archive.ArchiveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ArchiveApi {
    @POST(Endpoints.PLUBBING.CREATE_ARCHIVE)
    suspend fun createArchive(
        @Path("plubbingId") plubbingId : Int,
        @Body request: ArchiveUpdateRequest
    ): Response<ApiResponse<ArchiveUpdateResponse>>

    @GET(Endpoints.PLUBBING.FETCH_ALL_ARCHIVES)
    suspend fun fetchAllArchives(
        @Path("plubbingId") plubbingId : Int,
        @Query("cursorId") cursorId : Int
    ): Response<ApiResponse<ArchiveResponse>>

    @GET(Endpoints.PLUBBING.FETCH_DETAIL_ARCHIVE)
    suspend fun fetchDetailArchives(
        @Path("plubbingId") plubbingId : Int,
        @Path("archiveId") archiveId : Int
    ): Response<ApiResponse<ArchiveDetailResponse>>

    @PUT(Endpoints.PLUBBING.EDIT_ARCHIVE)
    suspend fun editArchive(
        @Path("plubbingId") plubbingId : Int,
        @Path("archiveId") archiveId : Int,
        @Body request: ArchiveUpdateRequest
    ): Response<ApiResponse<ArchiveUpdateResponse>>

    @DELETE(Endpoints.PLUBBING.DELETE_ARCHIVE)
    suspend fun deleteArchive(
        @Path("plubbingId") plubbingId : Int,
        @Path("archiveId") archiveId : Int
    ): Response<ApiResponse<ArchiveUpdateResponse>>
}