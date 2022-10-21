package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class SampleAuthInfoResponse(
    @SerializedName("data")
    val data : SampleAuthInfoData,

    @SerializedName("message")
    val message : String,

    @SerializedName("status")
    val status : String
)