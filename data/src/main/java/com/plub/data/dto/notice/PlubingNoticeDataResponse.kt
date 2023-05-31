package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingNoticeDataResponse(
    @SerializedName("noticeList")
    val noticeList : PlubingNoticeListResponse = PlubingNoticeListResponse()
) : DataDto
