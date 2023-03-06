package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.CommentEditRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutBoardCommentEditUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<CommentEditRequestVo, Flow<UiState<BoardCommentVo>>>() {
    override suspend operator fun invoke(request: CommentEditRequestVo): Flow<UiState<BoardCommentVo>> {
        return plubingBoardRepository.commentEdit(request)
    }
}