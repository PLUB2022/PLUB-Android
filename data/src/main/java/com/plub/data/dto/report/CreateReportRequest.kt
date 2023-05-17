package com.plub.data.dto.report

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CreateReportRequest(
    @SerializedName("reportType")
    val reportType : String = "",
    @SerializedName("reportTarget")
    val reportTarget : String = "",
    @SerializedName("reportTargetId")
    val reportTargetId : Int = 0,
    @SerializedName("reportReason")
    val reportReason : String = "",
    @SerializedName("plubbingId")
    val plubbingId : Int = 0,
) : DataDto