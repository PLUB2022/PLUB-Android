package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<TodoRequestVo, Flow<UiState<TodoTimelineVo>>>() {
    override suspend operator fun invoke(request: TodoRequestVo): Flow<UiState<TodoTimelineVo>> {
        return plubingTodoRepository.getTodoDetail(request)
    }
}