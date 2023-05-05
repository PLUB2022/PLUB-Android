package com.plub.data.dto.report

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ReportItemResponse(
    @SerializedName("reportType")
    val reportType : String = "",
    @SerializedName("detailContent")
    val detailContent : String = ""
) : DataDto
