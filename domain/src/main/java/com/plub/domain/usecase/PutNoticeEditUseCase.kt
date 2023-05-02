package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutNoticeEditUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<PostNoticeWriteRequestVo, Flow<UiState<NoticeVo>>>() {
    override suspend fun invoke(request: PostNoticeWriteRequestVo): Flow<UiState<NoticeVo>> {
        return noticeRepository.putNoticeEdit(request)
    }
}