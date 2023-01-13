package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringData(
    val content : List<RecommendationGatheringDataContentList>,
    val empty : Boolean,
    val first : Boolean,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
    val pageable : RecommendationGatheringDataPageable,
    val size : Int,
    val sort : RecommendationGatheringDataSort,
    val totalElements : Int,
    val totalPages : Int,
) : DataDto
