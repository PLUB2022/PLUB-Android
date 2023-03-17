package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.TodoWriteRequestVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutTodoEditUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<TodoWriteRequestVo, Flow<UiState<TodoItemVo>>>() {
    override suspend operator fun invoke(request: TodoWriteRequestVo): Flow<UiState<TodoItemVo>> {
        return plubingTodoRepository.putTodoEdit(request)
    }
}