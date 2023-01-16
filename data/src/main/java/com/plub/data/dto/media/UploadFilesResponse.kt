package com.plub.data.dto.media

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class UploadFilesResponse(
    @SerializedName("files")
    val files:List<UploadFileResponse> = emptyList(),
): DataDto