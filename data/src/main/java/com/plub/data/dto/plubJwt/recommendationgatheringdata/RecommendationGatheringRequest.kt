package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringRequest(
    val pageNum : Int,
    val accessToken : String
) : DataDto
