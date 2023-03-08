package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class BoardCommentListResponse(
    @SerializedName("totalPages")
    val totalPages: Int = -1,
    @SerializedName("totalElements")
    val totalElements: Int = -1,
    @SerializedName("content")
    val content: List<BoardCommentResponse> = emptyList(),
    @SerializedName("last")
    val last: Boolean = false,
) : DataDto