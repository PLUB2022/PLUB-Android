package com.plub.data.dto.bookmark

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubBookmarkResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int = 0,
    @SerializedName("isBookmarked")
    val isBookmarked: Boolean = false,
): DataDto