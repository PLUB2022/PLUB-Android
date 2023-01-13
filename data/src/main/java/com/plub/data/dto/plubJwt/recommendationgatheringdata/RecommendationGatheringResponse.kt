package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringData

data class RecommendationGatheringResponse(
    val plubbings : RecommendationGatheringData,
) : DataDto
