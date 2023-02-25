package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardDetailUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<BoardRequestVo, Flow<UiState<PlubingBoardVo>>>() {
    override suspend operator fun invoke(request: BoardRequestVo): Flow<UiState<PlubingBoardVo>> {
        return plubingBoardRepository.getBoardDetail(request)
    }
}