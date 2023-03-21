package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingNoticeResponse(
    @SerializedName("noticeId")
    val noticeId: Int = -1,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("isHost")
    val isHost: Boolean = false
) : DataDto