package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostTodoProofUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<TodoProofRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: TodoProofRequestVo): Flow<UiState<Unit>> {
        return plubingTodoRepository.postTodoProof(request)
    }
}