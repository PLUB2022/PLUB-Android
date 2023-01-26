package com.plub.data.dto.bookmark

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubBookmarkResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int,
    @SerializedName("isBookmarked")
    val isBookmarked: Boolean,
): DataDto