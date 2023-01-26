package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringDataResponseVo(
    val content : List<RecommendationGatheringResponseContentListVo>,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
) : DomainModel()