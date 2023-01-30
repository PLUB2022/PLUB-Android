package com.plub.data.dto.recruitdetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class Answers(
    @SerializedName("question")
    val question : String,
    @SerializedName("answer")
    val answer : String
) : DataDto