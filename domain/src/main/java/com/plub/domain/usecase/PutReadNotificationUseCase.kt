package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import com.plub.domain.repository.NoticeRepository
import com.plub.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutReadNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : UseCase<Int, Flow<UiState<Unit>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<Unit>> {
        return notificationRepository.readNotification(request)
    }
}