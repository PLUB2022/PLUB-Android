package com.plub.data.dto.login

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class SocialLoginRequest(
//    @SerializedName("socialType")
//    val socialLoginType: SocialLoginType,
//    @SerializedName("authorizationCode")
//    val authorizationCode: String,
//    @SerializedName("accessToken")
//    val accessToken: String,

    @SerializedName("authCode")
    val authCode: String,
    @SerializedName("isLoginSuccess")
    val isLoginSuccess: Boolean,
): DataDto