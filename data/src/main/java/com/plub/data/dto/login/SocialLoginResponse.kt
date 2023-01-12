package com.plub.data.dto.login

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SocialLoginResponse(
    @SerializedName("signToken")
    val signToken:String = "",
    @SerializedName("accessToken")
    val accessToken:String = "",
    @SerializedName("refreshToken")
    val refreshToken:String = "",
):DataDto
