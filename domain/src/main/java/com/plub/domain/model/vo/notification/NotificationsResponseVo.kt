package com.plub.domain.model.vo.notification

import com.plub.domain.model.DomainModel

data class NotificationsResponseVo(
    val notifications: List<NotificationResponseVo> = emptyList()
) : DomainModel

data class NotificationResponseVo(
    val notificationId: Int = -1,
    val notificationType: String = "",
    val targetEntity: String = "",
    val redirectTargetId: Int = -1,
    val title: String = "",
    val body: String = "",
    val createdAt: String = "",
    val isRead: Boolean = false
)
