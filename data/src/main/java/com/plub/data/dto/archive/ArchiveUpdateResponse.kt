package com.plub.data.dto.archive

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ArchiveUpdateResponse(
    @SerializedName("archiveId")
    val archiveId :Int = -1
):DataDto
