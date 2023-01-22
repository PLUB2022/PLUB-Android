package com.plub.data.dto.account

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class MyInfoResponse(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("socialType")
    val socialType: String = "",
    @SerializedName("birthday")
    val birthday: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("introduce")
    val introduce: String = "",
    @SerializedName("profileImage")
    val profileImage: String = ""
): DataDto