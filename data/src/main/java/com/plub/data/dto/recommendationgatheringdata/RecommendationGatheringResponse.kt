package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto

data class  RecommendationGatheringResponse(
    val totalPages : Int = -1,
    val totalElements : Int = -1,
    val last : Boolean = false,
    val content : List<RecommendationGatheringDataContentList> = emptyList()
) : DataDto