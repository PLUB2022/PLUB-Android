package com.plub.data.dto.login

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SocialLoginResponse(
    @SerializedName("authCode")
    val authCode:String = "",
):DataDto
