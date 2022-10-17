package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class SampleAccountResponse(
    @SerializedName("status")
    val status : String,
    @SerializedName("data")
    val data : Boolean,
    @SerializedName("message")
    val message : String
)