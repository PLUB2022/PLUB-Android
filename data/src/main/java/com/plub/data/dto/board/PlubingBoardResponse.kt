package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingBoardResponse(
    @SerializedName("feedId")
    val feedId: Int = -1,
    @SerializedName("feedType")
    val feedType: String = "",
    @SerializedName("viewType")
    val viewType: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("pin")
    val pin: Boolean = false,
    @SerializedName("profileImage")
    val profileImage: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("plubbingId")
    val plubbingId: Int = -1,
    @SerializedName("likeCount")
    val likeCount: Int = -1,
    @SerializedName("commentCount")
    val commentCount: Int = -1,
    @SerializedName("isAuthor")
    val isAuthor: Boolean = false,
    @SerializedName("isHost")
    val isHost: Boolean = false
) : DataDto