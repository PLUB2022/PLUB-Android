package com.plub.data.dto.recruitdetail

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class RecruitDetailJoinedAccountsDataResponse(
    @SerializedName("accountId")
    val accountId : Int,
    @SerializedName("profileImage")
    val profileImage: String
):DataDto