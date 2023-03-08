package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class BoardWriteRequest(
    @SerializedName("feedType")
    val feedType: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("feedImage")
    val feedImage: String = "",
) : DataDto