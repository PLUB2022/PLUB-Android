package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class PlubJwtTokenResponse(
    @SerializedName("data")
    val data : PlubJwtTokenDataResponse,

    @SerializedName("message")
    val message : String,

    @SerializedName("status")
    val status : String
)