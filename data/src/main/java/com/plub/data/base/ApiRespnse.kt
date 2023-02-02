package com.plub.data.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<D:DataDto>(
    @SerializedName("data")
    val data:D? = null,
    @SerializedName("message")
    val message:String = "",
    @SerializedName("statusCode")
    val statusCode:Int = -1,
)