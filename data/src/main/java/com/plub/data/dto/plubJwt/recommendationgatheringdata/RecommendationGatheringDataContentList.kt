package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataContentList (
    val plubbingId : Int,
    val name : String,
    val title : String,
    val mainImage : String,
    val introduce : String,
    val days : List<String>,
    val curAccountNum : Int,
    val isBookmarked : Boolean
) : DataDto