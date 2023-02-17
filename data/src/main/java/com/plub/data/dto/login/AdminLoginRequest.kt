package com.plub.data.dto.login

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class AdminLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
): DataDto