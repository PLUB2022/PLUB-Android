package com.plub.data.model

import com.google.gson.annotations.SerializedName

data class SampleAuthInfoData(
    @SerializedName("birthday")
    val birthday : String,

    @SerializedName("email")
    val email : String,

    @SerializedName("gender")
    val gender : String,

    @SerializedName("introduce")
    val introduce : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("profileImage")
    val profileImage : String,

    @SerializedName("socialType")
    val socialType : String
)
