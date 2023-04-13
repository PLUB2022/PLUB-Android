package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class TodoWriteRequest(
    @SerializedName("content")
    val content:String = "",
    @SerializedName("date")
    val date:String = ""
) : DataDto