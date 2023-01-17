package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringDataSortResponseVo (
    val empty : Boolean,
    val sorted : Boolean,
    val unsorted : Boolean
        ) : DomainModel()