package com.plub.data.dto.media

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class UploadFileResponse(
    @SerializedName("filename")
    val filename:String = "",
    @SerializedName("fileUrl")
    val fileUrl:String = ""
): DataDto