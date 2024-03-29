package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CommentCreateRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("parentCommentId")
    val parentCommentId: Int?,
): DataDto