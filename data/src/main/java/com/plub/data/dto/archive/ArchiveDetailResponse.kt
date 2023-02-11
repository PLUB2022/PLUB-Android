package com.plub.data.dto.archive

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ArchiveDetailResponse(
    @SerializedName("images")
    val images : List<String> = emptyList(),
    @SerializedName("sequence")
    val sequence : Int = -1,
    @SerializedName("createdAt")
    val createdAt : String = "",
) : DataDto
