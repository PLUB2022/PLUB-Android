package com.plub.data.model

import com.plub.data.base.DataDto

data class RecommendtaionGatheringResponse(
    val data : String,
    val message : String,
    val statusCode : Int
) : DataDto
