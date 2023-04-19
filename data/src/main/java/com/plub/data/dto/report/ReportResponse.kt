package com.plub.data.dto.report

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ReportResponse(
    @SerializedName("data")
    val data : List<ReportItemResponse> = emptyList()
) : DataDto
