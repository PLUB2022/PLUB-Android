package com.plub.data.model

import com.plub.data.base.DataDto
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringData

data class RecommendationGatheringResponse(
    val plubbings : RecommendationGatheringData,
) : DataDto