package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditCommentUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<BoardRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: BoardRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.editComment(request)
    }
}