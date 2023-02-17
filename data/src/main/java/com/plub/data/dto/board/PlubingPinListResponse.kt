package com.plub.data.dto.board

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class PlubingPinListResponse(
    @SerializedName("pinedFeedList")
    val pinedFeedList: List<PlubingBoardResponse> = emptyList(),
) : DataDto