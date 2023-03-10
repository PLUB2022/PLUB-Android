package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import kotlinx.coroutines.flow.Flow

interface PlubingTodoRepository {
    suspend fun getTimelineList(request: TodoGetTimelineRequestVo): Flow<UiState<TodoTimelineListVo>>
    suspend fun postTodoProof(request: TodoProofRequestVo): Flow<UiState<Unit>>
    suspend fun putTodoComplete(request: TodoRequestVo): Flow<UiState<Unit>>
    suspend fun putTodoCancel(request: TodoRequestVo): Flow<UiState<Unit>>
}