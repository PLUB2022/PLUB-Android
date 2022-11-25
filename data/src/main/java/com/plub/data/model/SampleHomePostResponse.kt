package com.plub.data.model

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SampleHomePostResponse(
    val authCode : String
) : DataDto
