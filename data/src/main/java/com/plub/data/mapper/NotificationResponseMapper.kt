package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.notification.NotificationResponse
import com.plub.data.dto.notification.NotificationsResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.model.vo.notification.NotificationsResponseVo

object NotificationResponseMapper: Mapper.ResponseMapper<NotificationResponse, NotificationResponseVo> {

    override fun mapDtoToModel(type: NotificationResponse?): NotificationResponseVo {
        return type?.run {
            NotificationResponseVo(
                notificationId = notificationId,
                notificationType = notificationType,
                targetEntity = targetEntity,
                redirectTargetId = redirectTargetId,
                title = title,
                body = body,
                createdAt = createdAt,
                isRead = isRead
            )
        } ?: NotificationResponseVo()
    }
}