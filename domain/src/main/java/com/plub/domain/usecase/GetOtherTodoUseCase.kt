package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.GetOtherTodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOtherTodoUseCase @Inject constructor(
    private val todoRepository: PlubingTodoRepository
): UseCase<GetOtherTodoRequestVo, Flow<UiState<TodoTimelineListVo>>>() {
    override suspend operator fun invoke(request: GetOtherTodoRequestVo): Flow<UiState<TodoTimelineListVo>> {
        return todoRepository.getOtherTimelineList(request)
    }
}