package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AppNoticeDataResponse(
    @SerializedName("announcementList")
    val announcementList : AppNoticeListResponse = AppNoticeListResponse()
) : DataDto
