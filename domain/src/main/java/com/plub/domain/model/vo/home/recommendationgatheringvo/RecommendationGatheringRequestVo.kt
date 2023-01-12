package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringRequestVo(
    val pageNum : Int,
    val accessToken : String
) : DomainModel()
