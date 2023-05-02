package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingNoticeListResponse(
    @SerializedName("last")
    val last: Boolean = false,
    @SerializedName("content")
    val content: List<PlubingNoticeResponse> = emptyList(),
) : DataDto