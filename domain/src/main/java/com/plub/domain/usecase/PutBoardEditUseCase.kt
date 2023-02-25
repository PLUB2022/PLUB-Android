package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardEditRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutBoardEditUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<BoardEditRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: BoardEditRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.feedEdit(request)
    }
}