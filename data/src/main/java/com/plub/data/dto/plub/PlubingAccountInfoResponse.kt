package com.plub.data.dto.plub

import com.google.gson.annotations.SerializedName

data class PlubingAccountInfoResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("profileImage")
    val profileImage: String = "",
)