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
import com.plub.domain.error.TodoError
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.*
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingTodoRepositoryImpl @Inject constructor(private val todoApi: TodoApi) : PlubingTodoRepository, BaseRepository() {

    override suspend fun getTimelineList(request: TodoGetTimelineRequestVo): Flow<UiState<TodoTimelineListVo>> {
        return apiLaunch(apiCall = { todoApi.getTimelines(request.plubbingId, request.cursorId) }, TodoTimelineListResponseMapper.apply {
            setViewType(request.todoItemViewType)
        }){
            TodoError.make(it)
        }
    }

    override suspend fun getOtherTimelineList(request: GetOtherTodoRequestVo): Flow<UiState<TodoTimelineListVo>> {
        return apiLaunch(apiCall = { todoApi.getOtherTimelines(request.plubbingId, request.accountId, request.cursorId) }, TodoTimelineListResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun postTodoProof(request: TodoProofRequestVo): Flow<UiState<Unit>> {
        val proofRequest = TodoProofRequest(request.proofImageUrl)
        return apiLaunch(apiCall = { todoApi.postTodoProof(request.plubbingId, request.todoListId, proofRequest) }, UnitResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun putTodoComplete(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { todoApi.putTodoComplete(request.plubbingId, request.todoId) }, UnitResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun putTodoCancel(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { todoApi.putTodoCancel(request.plubbingId, request.todoId) }, UnitResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun getTodoDays(request: GetTodoDaysRequestVo): Flow<UiState<List<Int>>> {
        return apiLaunch(apiCall = { todoApi.getTodoDays(request.plubbingId, request.year, request.month) }, GetTodoDaysResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun getMyTodoListInDay(request: GetMyTodoListInDayRequestVo): Flow<UiState<TodoTimelineVo>> {
        return apiLaunch(apiCall = { todoApi.getMyTodoListInDay(request.plubbingId, request.date) }, TodoTimelineResponseMapper.apply {
            setViewType(TodoItemViewType.PLANNER)
        }){
            TodoError.make(it)
        }
    }

    override suspend fun deleteTodo(request: TodoRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { todoApi.deleteTodo(request.plubbingId, request.todoId) }, UnitResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun putTodoEdit(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>> {
        val body = TodoWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { todoApi.putTodoEdit(request.plubbingId, request.todoId, body) }, TodoItemResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun postTodoCreate(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>> {
        val body = TodoWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { todoApi.postTodoCreate(request.plubbingId, body) }, TodoItemResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun putTodoLikeToggle(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>> {
        return apiLaunch(apiCall = { todoApi.putLikeToggleTodo(request.plubbingId, request.timelineId) }, TodoTimelineResponseMapper){
            TodoError.make(it)
        }
    }

    override suspend fun getTodoDetail(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>> {
        return apiLaunch(apiCall = { todoApi.getTodoDetail(request.plubbingId, request.timelineId) }, TodoTimelineResponseMapper.apply {
            setViewType(TodoItemViewType.DETAIL)
        }){
            TodoError.make(it)
        }
    }
}