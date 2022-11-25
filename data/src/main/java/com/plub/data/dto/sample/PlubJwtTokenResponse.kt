package com.plub.data.model

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataEntity

data class PlubJwtTokenResponse(
    @SerializedName("data")
    val data : PlubJwtTokenData?
):DataEntity()

data class PlubJwtTokenData(
    @SerializedName("accessToken")
    val accessToken : String,

    @SerializedName("refreshToken")
    val refreshToken : String
)