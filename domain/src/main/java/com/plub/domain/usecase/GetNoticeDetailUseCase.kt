package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeDetailUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<NoticeRequestVo, Flow<UiState<NoticeVo>>>() {
    override suspend fun invoke(request: NoticeRequestVo): Flow<UiState<NoticeVo>> {
        return if(request.noticeType == NoticeType.APP) noticeRepository.getAppNoticeDetail(request)
        else noticeRepository.getNoticeDetail(request)
    }
}