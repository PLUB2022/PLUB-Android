package com.plub.data.dto.media

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class DeleteFileResponse(
    @SerializedName("data")
    val data : String = ""
) :DataDto