package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<TodoGetTimelineRequestVo, Flow<UiState<TodoTimelineListVo>>>() {
    override suspend operator fun invoke(request: TodoGetTimelineRequestVo): Flow<UiState<TodoTimelineListVo>> {
        return plubingTodoRepository.getTimelineList(request)
    }
}