package com.plub.data.dto.recruitdetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AccountDataResponse(
    @SerializedName("accountId")
    val accountId : Int = -1,
    @SerializedName("accountName")
    val accountName: String = "",
    @SerializedName("profileImage")
    val profileImage : String = "",
    @SerializedName("createdAt")
    val createdAt : String = "",
    @SerializedName("answers")
    val answers : List<AnswersDataResponse> = emptyList()
) : DataDto