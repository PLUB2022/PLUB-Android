package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.archive.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ArchiveApi {
    companion object{
        const val PATH_PLUBBING_ID = "plubbingId"
        const val QUERY_CURSOR_ID = "cursorId"
        const val PATH_ARCHIVE_ID = "archiveId"
    }
    @POST(Endpoints.PLUBBING.CREATE_ARCHIVE)
    suspend fun createArchive(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Body request: ArchiveUpdateRequest
    ): Response<ApiResponse<ArchiveUpdateResponse>>

    @GET(Endpoints.PLUBBING.FETCH_ALL_ARCHIVES)
    suspend fun fetchAllArchives(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Query(QUERY_CURSOR_ID) cursorId : Int
    ): Response<ApiResponse<ArchiveResponse>>

    @GET(Endpoints.PLUBBING.FETCH_DETAIL_ARCHIVE)
    suspend fun fetchDetailArchives(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Path(PATH_ARCHIVE_ID) archiveId : Int
    ): Response<ApiResponse<ArchiveDetailResponse>>

    @PUT(Endpoints.PLUBBING.EDIT_ARCHIVE)
    suspend fun editArchive(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Path(PATH_ARCHIVE_ID) archiveId : Int,
        @Body request: ArchiveUpdateRequest
    ): Response<ApiResponse<ArchiveContentResponse>>

    @DELETE(Endpoints.PLUBBING.DELETE_ARCHIVE)
    suspend fun deleteArchive(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Path(PATH_ARCHIVE_ID) archiveId : Int
    ): Response<ApiResponse<ArchiveContentResponse>>
}