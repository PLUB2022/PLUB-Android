package com.plub.data.dto.sample

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class JWTTokenReIssueRequest(
    @SerializedName("refreshToken")
    val refreshToken : String
):DataDto