package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.notice.NoticeCommentCreateRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostNoticeCommentCreateUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<NoticeCommentCreateRequestVo, Flow<UiState<BoardCommentVo>>>() {
    override suspend operator fun invoke(request: NoticeCommentCreateRequestVo): Flow<UiState<BoardCommentVo>> {
        return noticeRepository.commentCreate(request)
    }
}