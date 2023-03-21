package com.plub.data.dto.account

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AccountInfoResponse(
    @SerializedName("accountId")
    val accountId: Int = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("profileImage")
    val profileImage: String? = ""
): DataDto