package com.plub.data.dto.report

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ReportDetailResponse(
    @SerializedName("reportId")
    val reportId : Int = 0,
    @SerializedName("targetId")
    val targetId : Int = 0,
    @SerializedName("reportType")
    val reportType : String = "",
    @SerializedName("reportTarget")
    val reportTarget : String = "",
    @SerializedName("reportTitle")
    val reportTitle : String = "",
    @SerializedName("reportContent")
    val reportContent : String = "",
    @SerializedName("reportedAt")
    val reportedAt : String = "",
) : DataDto