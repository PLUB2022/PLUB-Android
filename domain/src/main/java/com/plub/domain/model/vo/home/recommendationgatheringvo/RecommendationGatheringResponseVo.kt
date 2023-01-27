package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel
import com.plub.domain.model.vo.plub.PlubCardVo

data class RecommendationGatheringResponseVo (
    val content : List<PlubCardVo>,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
) : DomainModel()