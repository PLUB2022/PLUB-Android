package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataPageable(
    val sort: RecommendationGatheringDataSort,
    val offset : Int,
    val pageNumber : Int,
    val pageSize : Int,
    val paged : Boolean,
    val unpaged : Boolean
) : DataDto