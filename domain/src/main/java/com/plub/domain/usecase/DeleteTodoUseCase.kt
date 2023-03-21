package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<TodoRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: TodoRequestVo): Flow<UiState<Unit>> {
        return plubingTodoRepository.deleteTodo(request)
    }
}