package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import kotlinx.coroutines.flow.Flow

interface PlubingTodoRepository {
    suspend fun getTimelineList(request: TodoRequestVo): Flow<UiState<TodoTimelineListVo>>
    suspend fun postTodoProof(request: TodoProofRequestVo): Flow<UiState<Unit>>
}