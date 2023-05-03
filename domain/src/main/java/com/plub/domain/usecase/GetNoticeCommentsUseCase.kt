package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.notice.GetNoticeCommentsRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeCommentsUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<GetNoticeCommentsRequestVo, Flow<UiState<BoardCommentListVo>>>() {
    override suspend operator fun invoke(request: GetNoticeCommentsRequestVo): Flow<UiState<BoardCommentListVo>> {
        return noticeRepository.commentGetList(request)
    }
}