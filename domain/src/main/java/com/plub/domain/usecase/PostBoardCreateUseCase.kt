package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCreateRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostBoardCreateUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<BoardCreateRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: BoardCreateRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.feedCreate(request)
    }
}