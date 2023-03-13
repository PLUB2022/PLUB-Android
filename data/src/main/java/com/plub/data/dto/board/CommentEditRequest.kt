package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CommentEditRequest(
    @SerializedName("content")
    val content: String,
): DataDto