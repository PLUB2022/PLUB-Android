package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class BoardCommentResponse(
    @SerializedName("commentId")
    val commentId: Int = -1,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("profileImage")
    val profileImage: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("isCommentAuthor")
    val isCommentAuthor: Boolean = false,
    @SerializedName("isFeedAuthor")
    val isFeedAuthor: Boolean = false,
    @SerializedName("commentType")
    val commentType: String = "",
    @SerializedName("parentCommentId")
    val parentCommentId: Int? = -1,
    @SerializedName("parentCommentNickname")
    val parentCommentNickname: String? = "",
) : DataDto