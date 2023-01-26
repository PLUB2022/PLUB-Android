package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringResponseVo (
    val content : List<RecommendationGatheringResponseContentListVo>,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
) : DomainModel()