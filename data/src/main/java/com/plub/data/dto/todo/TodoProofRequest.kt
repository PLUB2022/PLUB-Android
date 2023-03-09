package com.plub.data.dto.todo

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class TodoProofRequest(
    @SerializedName("proofImage")
    val proofImage: String = "",
) : DataDto