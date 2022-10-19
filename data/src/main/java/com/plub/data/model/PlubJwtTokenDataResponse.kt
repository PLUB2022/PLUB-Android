package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class PlubJwtTokenDataResponse(
    @SerializedName("accessToken")
    val accessToken : String,

    @SerializedName("refreshToken")
    val refreshToken : String
)
