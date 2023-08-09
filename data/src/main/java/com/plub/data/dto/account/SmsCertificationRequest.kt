package com.plub.data.dto.account

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SmsCertificationRequest(
    @SerializedName("phone")
    val phone : String = "",
    @SerializedName("certificationNum")
    val certificationNum : String = ""
) : DataDto
