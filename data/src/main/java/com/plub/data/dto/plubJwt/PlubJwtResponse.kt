package com.plub.data.dto.plubJwt

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubJwtResponse(
    @SerializedName("accessToken")
    val accessToken : String = "",

    @SerializedName("refreshToken")
    val refreshToken : String = ""
):DataDto