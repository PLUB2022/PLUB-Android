package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteNoticeCommentUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<NoticeRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: NoticeRequestVo): Flow<UiState<Unit>> {
        return noticeRepository.commentDelete(request)
    }
}