package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.account.AccountInfoResponse

data class TodoTimelineResponse(
    @SerializedName("todoTimelineId")
    val todoTimelineId: Int = -1,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("totalLikes")
    val totalLikes: Int = -1,
    @SerializedName("accountInfo")
    val accountInfo: AccountInfoResponse = AccountInfoResponse(),
    @SerializedName("todoList")
    val todoList: List<TodoItemResponse> = emptyList(),
) : DataDto