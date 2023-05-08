package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.notification.NotificationsResponseVo
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.repository.NotificationRepository
import com.plub.domain.repository.PlubingTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : UseCase<Unit, Flow<UiState<NotificationsResponseVo>>>() {
    override suspend operator fun invoke(request: Unit): Flow<UiState<NotificationsResponseVo>> {
        return notificationRepository.getMyNotifications(request)
    }
}