package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringData

data class RecommendationGatheringResponse(
    val plubbings : RecommendationGatheringData,
) : DataDto