package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class TodoItemResponse(
    @SerializedName("todoId")
    val todoId: Int = -1,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("isChecked")
    val isChecked: Boolean = false,
    @SerializedName("isProof")
    val isProof: Boolean = false,
    @SerializedName("proofImage")
    val proofImage: String = "",
    @SerializedName("likes")
    val likes: Int = -1,
    @SerializedName("isAuthor")
    val isAuthor: Boolean = false,
) : DataDto