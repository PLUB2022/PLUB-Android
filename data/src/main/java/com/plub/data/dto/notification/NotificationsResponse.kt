package com.plub.data.dto.notification

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class NotificationsResponse(
    @SerializedName("notifications")
    val notifications: List<NotificationResponse> = emptyList()
) : DataDto

data class NotificationResponse(
    @SerializedName("notificationId")
    val notificationId: Int = -1,
    @SerializedName("notificationType")
    val notificationType: String = "",
    @SerializedName("targetEntity")
    val targetEntity: String = "",
    @SerializedName("redirectTargetId")
    val redirectTargetId: Int = -1,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("isRead")
    val isRead: Boolean = false
)
