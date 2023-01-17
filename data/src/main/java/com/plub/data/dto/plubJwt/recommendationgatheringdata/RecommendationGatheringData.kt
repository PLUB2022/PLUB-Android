package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringData(
    val content : List<RecommendationGatheringDataContentList>,
    val pageable : RecommendationGatheringDataPageable,
    val last : Boolean,
    val totalPages : Int,
    val totalElements : Int,
    val size : Int,
    val number : Int,
    val sort : RecommendationGatheringDataSort,
    val first : Boolean,
    val numberOfElements : Int,
    val empty : Boolean
) : DataDto
