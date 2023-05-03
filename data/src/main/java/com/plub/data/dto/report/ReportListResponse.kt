package com.plub.data.dto.report

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ReportListResponse(
    @SerializedName("reportList")
    val reportList : List<ReportItemResponse> = emptyList()
) : DataDto
