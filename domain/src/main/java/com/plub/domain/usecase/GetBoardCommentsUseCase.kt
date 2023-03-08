package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.GetBoardCommentsRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardCommentsUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<GetBoardCommentsRequestVo, Flow<UiState<BoardCommentListVo>>>() {
    override suspend operator fun invoke(request: GetBoardCommentsRequestVo): Flow<UiState<BoardCommentListVo>> {
        return plubingBoardRepository.commentGetList(request)
    }
}