package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardPinsUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<Int, Flow<UiState<List<PlubingBoardVo>>>>() {
    override suspend operator fun invoke(request: Int): Flow<UiState<List<PlubingBoardVo>>> {
        return plubingBoardRepository.feedGetPinedList(request)
    }
}