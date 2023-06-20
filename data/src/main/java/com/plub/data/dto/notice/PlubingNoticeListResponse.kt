package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingNoticeListResponse(
    @SerializedName("totalElements")
    val totalElements : Int = 0,
    @SerializedName("last")
    val last: Boolean = false,
    @SerializedName("content")
    val content: List<PlubingNoticeResponse> = emptyList(),
) : DataDto