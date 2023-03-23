package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.GetMyTodoListInDayRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyTodoListInDayUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<GetMyTodoListInDayRequestVo, Flow<UiState<TodoTimelineVo>>>() {
    override suspend operator fun invoke(request: GetMyTodoListInDayRequestVo): Flow<UiState<TodoTimelineVo>> {
        return plubingTodoRepository.getMyTodoListInDay(request)
    }
}