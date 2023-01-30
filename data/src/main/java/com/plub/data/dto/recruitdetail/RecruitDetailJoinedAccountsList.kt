package com.plub.data.dto.recruitdetail

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class RecruitDetailJoinedAccountsList(
    @SerializedName("accountId")
    val accountId : Int,
    @SerializedName("profileImage")
    val profileImage: String
):DataDto