package com.plub.data.dto.signUp

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class NicknameCheckResponse(
    @SerializedName("isAvailableNickname")
    val isAvailableNickname:Boolean = false,
):DataDto
