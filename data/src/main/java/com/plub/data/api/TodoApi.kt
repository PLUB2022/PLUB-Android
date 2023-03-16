package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.todo.GetTodoDaysResponse
import com.plub.data.dto.todo.TodoItemResponse
import com.plub.data.dto.todo.TodoProofRequest
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.data.dto.todo.TodoWriteRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApi {
    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val PATH_TIMELINE_ID = "timelineId"
        private const val PATH_TODO_LIST_ID = "todolistId"
        private const val PATH_TODO_YEAR = "year"
        private const val PATH_TODO_ID = "todoId"
        private const val PATH_TODO_MONTH = "month"
        private const val PATH_DATE = "todoDate"
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

    @GET(Endpoints.PLUBBING.TODO.TODO_DAYS_IN_MONTH)
    suspend fun getTodoDays(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_YEAR) month: Int,
        @Path(PATH_TODO_MONTH) year: Int,
    ): Response<ApiResponse<GetTodoDaysResponse>>

    @GET(Endpoints.PLUBBING.TODO.MY_TODO_IN_DAY)
    suspend fun getMyTodoListInDay(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_DATE) date: String
    ): Response<ApiResponse<TodoTimelineResponse>>

    @POST(Endpoints.PLUBBING.TODO.TODO_CREATE)
    suspend fun postTodoCreate(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Body request: TodoWriteRequest,
    ): Response<ApiResponse<TodoItemResponse>>

    @PUT(Endpoints.PLUBBING.TODO.TODO_EDIT)
    suspend fun putTodoEdit(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_ID) todoId: Int,
        @Body request: TodoWriteRequest,
    ): Response<ApiResponse<TodoItemResponse>>

    @DELETE(Endpoints.PLUBBING.TODO.TODO_DELETE)
    suspend fun deleteTodo(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TODO_ID) todoId: Int
    ): Response<ApiResponse<DataDto.DTO>>

    @PUT(Endpoints.PLUBBING.TODO.TODO_LIKE_TOGGLE)
    suspend fun putLikeToggleTodo(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TIMELINE_ID) timelineId: Int
    ): Response<ApiResponse<TodoTimelineResponse>>

    @GET(Endpoints.PLUBBING.TODO.TODO_DETAIL)
    suspend fun getTodoDetail(
        @Path(PATH_PLUBING_ID) plubbingId: Int,
        @Path(PATH_TIMELINE_ID) timelineId: Int
    ): Response<ApiResponse<TodoTimelineResponse>>
}