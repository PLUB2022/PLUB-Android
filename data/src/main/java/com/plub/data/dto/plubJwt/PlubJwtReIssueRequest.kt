package com.plub.data.dto.plubJwt

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubJwtReIssueRequest(
    @SerializedName("refreshToken")
    val refreshToken : String
):DataDto