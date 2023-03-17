package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.todo.GetTodoDaysRequestVo
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoDaysInMonthUseCase @Inject constructor(
    private val plubingTodoRepository: PlubingTodoRepository
) : UseCase<GetTodoDaysRequestVo, Flow<UiState<List<Int>>>>() {
    override suspend operator fun invoke(request: GetTodoDaysRequestVo): Flow<UiState<List<Int>>> {
        return plubingTodoRepository.getTodoDays(request)
    }
}