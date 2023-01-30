package com.plub.data.dto.applyrecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class Questions(
    @SerializedName("id")
    val id : Int,
    @SerializedName("question")
    val question: String
) : DataDto