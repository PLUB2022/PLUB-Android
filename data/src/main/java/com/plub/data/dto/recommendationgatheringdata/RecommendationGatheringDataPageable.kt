package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataPageable(
    val sort: RecommendationGatheringDataSort,
    val offset : Int,
    val pageSize : Int,
    val pageNumber : Int,
    val unpaged : Boolean,
    val paged : Boolean
) : DataDto