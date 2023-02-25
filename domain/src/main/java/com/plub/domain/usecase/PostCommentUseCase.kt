package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.PostCommentRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<PostCommentRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: PostCommentRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.postComment(request)
    }
}