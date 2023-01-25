package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringResponseVo (
    val plubbings : RecommendationGatheringDataResponseVo
) : DomainModel()