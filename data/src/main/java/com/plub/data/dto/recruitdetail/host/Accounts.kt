package com.plub.data.dto.recruitdetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.recruitdetail.host.Answers

data class Accounts(
    @SerializedName("accountName")
    val accountName: String,
    @SerializedName("profileImage")
    val profileImage : String,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("answers")
    val answers : List<Answers>
) : DataDto