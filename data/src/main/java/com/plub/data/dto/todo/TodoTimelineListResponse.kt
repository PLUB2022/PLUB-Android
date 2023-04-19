package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class TodoTimelineListResponse(
    @SerializedName("content")
    val content: List<TodoTimelineResponse> = emptyList(),
    @SerializedName("last")
    val last: Boolean = false,
) : DataDto