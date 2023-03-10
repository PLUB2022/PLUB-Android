package com.plub.data.repository

import com.plub.data.api.TodoApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.todo.TodoProofRequest
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.todo.TodoTimelineListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingTodoRepositoryImpl @Inject constructor(private val todoApi: TodoApi) : PlubingTodoRepository, BaseRepository() {

    override suspend fun getTimelineList(request: TodoGetTimelineRequestVo): Flow<UiState<TodoTimelineListVo>> {
        return apiLaunch(todoApi.getTimelines(request.plubbingId, request.cursorId), TodoTimelineListResponseMapper.apply {
            setViewType(request.todoItemViewType)
        })
    }

    override suspend fun postTodoProof(request: TodoProofRequestVo): Flow<UiState<Unit>> {
        val proofRequest = TodoProofRequest(request.proofImageUrl)
        return apiLaunch(todoApi.postTodoProof(request.plubbingId, request.todoListId, proofRequest), UnitResponseMapper)
    }

    override suspend fun putTodoComplete(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(todoApi.putTodoComplete(request.plubbingId, request.todoId), UnitResponseMapper)
    }

    override suspend fun putTodoCancel(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(todoApi.putTodoCancel(request.plubbingId, request.todoId), UnitResponseMapper)
    }
}