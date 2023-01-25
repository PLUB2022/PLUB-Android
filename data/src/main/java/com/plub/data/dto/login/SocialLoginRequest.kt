package com.plub.data.dto.login

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SocialLoginRequest(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("authorizationCode")
    val authorizationCode: String,
    @SerializedName("socialType")
    val socialType: String,
): DataDto