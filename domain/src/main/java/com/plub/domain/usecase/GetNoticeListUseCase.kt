package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.vo.notice.GetNoticeListRequestVo
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<GetNoticeListRequestVo, Flow<UiState<NoticeListVo>>>() {
    override suspend operator fun invoke(request: GetNoticeListRequestVo): Flow<UiState<NoticeListVo>> {
        return if(request.noticeType == NoticeType.APP) noticeRepository.getAppNoticeList()
        else noticeRepository.getPlubingNoticeList(request.plubbingId, request.cursorId)
    }
}