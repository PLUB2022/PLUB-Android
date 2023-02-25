package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.GetCommentListVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<GetCommentListVo, Flow<UiState<BoardCommentListVo>>>() {
    override suspend operator fun invoke(request: GetCommentListVo): Flow<UiState<BoardCommentListVo>> {
        return plubingBoardRepository.getCommentList(request)
    }
}