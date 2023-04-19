package com.plub.data.dto.archive

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ArchiveUpdateRequest(
    @SerializedName("title")
    val title : String ="",
    @SerializedName("images")
    val images : List<String> = emptyList()
) : DataDto
