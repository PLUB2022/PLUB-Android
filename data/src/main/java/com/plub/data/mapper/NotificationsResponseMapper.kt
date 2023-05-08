package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.notification.NotificationsResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import com.plub.domain.model.vo.notification.NotificationsResponseVo

object NotificationsResponseMapper: Mapper.ResponseMapper<NotificationsResponse, NotificationsResponseVo> {

    override fun mapDtoToModel(type: NotificationsResponse?): NotificationsResponseVo {
        return type?.run {
            NotificationsResponseVo(notifications = notifications.map {
                NotificationResponseMapper.mapDtoToModel(it)
            })
        } ?: NotificationsResponseVo()
    }
}