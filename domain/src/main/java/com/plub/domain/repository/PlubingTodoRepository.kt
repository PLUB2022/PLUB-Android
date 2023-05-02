package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.todo.*
import kotlinx.coroutines.flow.Flow

interface PlubingTodoRepository {
    suspend fun getTimelineList(request: TodoGetTimelineRequestVo): Flow<UiState<TodoTimelineListVo>>
    suspend fun getOtherTimelineList(request: GetOtherTodoRequestVo): Flow<UiState<TodoTimelineListVo>>
    suspend fun postTodoProof(request: TodoProofRequestVo): Flow<UiState<Unit>>
    suspend fun putTodoComplete(request: TodoRequestVo): Flow<UiState<Unit>>
    suspend fun putTodoCancel(request: TodoRequestVo): Flow<UiState<Unit>>
    suspend fun getTodoDays(request: GetTodoDaysRequestVo): Flow<UiState<List<Int>>>
    suspend fun getMyTodoListInDay(request: GetMyTodoListInDayRequestVo): Flow<UiState<TodoTimelineVo>>
    suspend fun deleteTodo(request: TodoRequestVo): Flow<UiState<Unit>>
    suspend fun postTodoCreate(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>>
    suspend fun putTodoEdit(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>>
    suspend fun putTodoLikeToggle(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>>
    suspend fun getTodoDetail(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>>
}