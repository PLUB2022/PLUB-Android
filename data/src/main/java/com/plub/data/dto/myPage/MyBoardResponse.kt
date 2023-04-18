package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.plub.PlubInfoResponse

data class MyBoardResponse(
    @SerializedName("plubbingInfo")
    val plubbingInfo : PlubInfoResponse = PlubInfoResponse(),
    @SerializedName("myFeedList")
    val myFeedList: PlubingBoardListResponse = PlubingBoardListResponse()
) : DataDto