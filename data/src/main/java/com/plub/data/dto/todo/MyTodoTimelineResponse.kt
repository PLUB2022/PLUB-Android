package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyTodoTimelineResponse(
    @SerializedName("totalElements")
    val totalElements : Int = -1,
    @SerializedName("last")
    val last : Boolean = false,
    @SerializedName("content")
    val content : List<TodoTimelineResponse> = emptyList()
) : DataDto
