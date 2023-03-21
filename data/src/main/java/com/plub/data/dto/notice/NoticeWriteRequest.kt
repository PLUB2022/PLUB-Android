package com.plub.data.dto.notice

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class NoticeWriteRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
) : DataDto