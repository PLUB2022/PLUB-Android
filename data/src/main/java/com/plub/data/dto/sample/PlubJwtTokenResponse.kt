package com.plub.data.model

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubJwtTokenResponse(
    @SerializedName("data")
    val data : PlubJwtTokenData?
):DataDto

data class PlubJwtTokenData(
    @SerializedName("accessToken")
    val accessToken : String,

    @SerializedName("refreshToken")
    val refreshToken : String
)