package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringResponseContentListVo(
    val plubbingId : Int,
    val name : String,
    val title : String,
    val mainImage : String,
    val introduce : String,
    val days : List<String>,
    val curAccountNum : Int,
    val isBookmarked : Boolean
) : DomainModel()
