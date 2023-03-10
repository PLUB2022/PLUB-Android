package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.todo.TodoProofRequest
import com.plub.data.dto.todo.TodoTimelineListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApi {
    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_TODO_LIST_ID = "todolistId"
        private const val QUERY_CURSOR_ID = "cursorId"
    }

    @GET(Endpoints.PLUBBING.TODO.TIMELINES)
    suspend fun getTimelines(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Query(QUERY_CURSOR_ID) cursorId: Int
    ): Response<ApiResponse<TodoTimelineListResponse>>

    @POST(Endpoints.PLUBBING.TODO.PROOF)
    suspend fun postTodoProof(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_LIST_ID) todoId: Int,
        @Body request: TodoProofRequest,
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.TODO.COMPLETE)
    suspend fun putTodoComplete(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_LIST_ID) todoId: Int,
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.TODO.CANCEL)
    suspend fun putTodoCancel(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_LIST_ID) todoId: Int,
    ): Response<ApiResponse<DataDto.DTO>>
}