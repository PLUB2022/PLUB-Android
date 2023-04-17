package com.plub.data.dto.archive

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ArchiveResponse(
    @SerializedName("totalElements")
    val totalElements : Int = -1,
    @SerializedName("last")
    val last : Boolean = false,
    @SerializedName("content")
    val content : List<ArchiveContentResponse> = emptyList()

):DataDto