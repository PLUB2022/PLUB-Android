package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringData(
    val totalPages : Int,
    val totalElements : Int,
    val last : Boolean,
    val content : List<RecommendationGatheringDataContentList>,
) : DataDto