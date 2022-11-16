package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class PlubJwtTokenResponse(
    @SerializedName("data")
    val data : PlubJwtTokenData
)

data class PlubJwtTokenData(
    @SerializedName("accessToken")
    val accessToken : String,

    @SerializedName("refreshToken")
    val refreshToken : String
)