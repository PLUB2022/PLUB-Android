package com.plub.data.util

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ApiResponse<D:DataDto>(
    @SerializedName("data")
    val data:D? = null,
    @SerializedName("message")
    val message:String = "",
    @SerializedName("statusCode")
    val statusCode:Int = -1,
)