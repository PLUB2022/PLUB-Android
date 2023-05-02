package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.notice.NoticeCommentEditRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutNoticeCommentEditUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<NoticeCommentEditRequestVo, Flow<UiState<BoardCommentVo>>>() {
    override suspend operator fun invoke(request: NoticeCommentEditRequestVo): Flow<UiState<BoardCommentVo>> {
        return noticeRepository.commentEdit(request)
    }
}