package com.plub.data.dto.recruitdetail

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class RecruitDetailJoinedAccountsDataResponse(
    @SerializedName("accountId")
    val accountId : Int = -1,
    @SerializedName("profileImage")
    val profileImage: String = "",
    @SerializedName("nickname")
    val nickname : String = ""
):DataDto