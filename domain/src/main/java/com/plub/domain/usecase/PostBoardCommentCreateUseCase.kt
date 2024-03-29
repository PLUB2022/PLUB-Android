package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostBoardCommentCreateUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<CommentCreateRequestVo, Flow<UiState<BoardCommentVo>>>() {
    override suspend operator fun invoke(request: CommentCreateRequestVo): Flow<UiState<BoardCommentVo>> {
        return plubingBoardRepository.commentCreate(request)
    }
}