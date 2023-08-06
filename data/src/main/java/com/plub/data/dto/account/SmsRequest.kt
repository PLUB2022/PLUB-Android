package com.plub.data.dto.account

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SmsRequest(
    @SerializedName("to")
    val to : String = ""
) : DataDto
