package com.plub.data.dto.plubJwt

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SampleHomePostRequest(
    @SerializedName("authCode")
    val authCode : String,

    @SerializedName("isLoginSuccess")
    val isLoginSuccess : Boolean
) : DataDto
