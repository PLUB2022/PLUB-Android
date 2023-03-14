package com.plub.data.repository

import com.plub.data.api.TodoApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.todo.TodoProofRequest
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.todo.GetTodoDaysResponseMapper
import com.plub.data.mapper.todo.TodoItemResponseMapper
import com.plub.data.mapper.todo.TodoTimelineListResponseMapper
import com.plub.data.mapper.todo.TodoTimelineResponseMapper
import com.plub.data.mapper.todo.TodoWriteRequestMapper
import com.plub.domain.UiState
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.GetMyTodoListInDayRequestVo
import com.plub.domain.model.vo.todo.GetTodoDaysRequestVo
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.model.vo.todo.TodoWriteRequestVo
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

    override suspend fun getTodoDays(request: GetTodoDaysRequestVo): Flow<UiState<List<Int>>> {
        return apiLaunch(todoApi.getTodoDays(request.plubbingId, request.year, request.month), GetTodoDaysResponseMapper)
    }

    override suspend fun getMyTodoListInDay(request: GetMyTodoListInDayRequestVo): Flow<UiState<TodoTimelineVo>> {
        return apiLaunch(todoApi.getMyTodoListInDay(request.plubbingId, request.date), TodoTimelineResponseMapper.apply {
            setViewType(TodoItemViewType.PLANNER)
        })
    }

    override suspend fun deleteTodo(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(todoApi.deleteTodo(request.plubbingId, request.todoId), UnitResponseMapper)
    }

    override suspend fun putTodoEdit(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>> {
        val body = TodoWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(todoApi.putTodoEdit(request.plubbingId, request.todoId, body), TodoItemResponseMapper)
    }

    override suspend fun postTodoCreate(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>> {
        val body = TodoWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(todoApi.postTodoCreate(request.plubbingId, body), TodoItemResponseMapper)
    }

    override suspend fun putTodoLikeToggle(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>> {
        return apiLaunch(todoApi.putLikeToggleTodo(request.plubbingId, request.timelineId), TodoTimelineResponseMapper)
    }
}