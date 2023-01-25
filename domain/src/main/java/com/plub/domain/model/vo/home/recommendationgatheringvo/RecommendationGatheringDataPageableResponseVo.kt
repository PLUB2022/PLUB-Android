package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringDataPageableResponseVo(
    val sort: RecommendationGatheringDataSortResponseVo,
    val offset : Int,
    val pageSize : Int,
    val pageNumber : Int,
    val unpaged : Boolean,
    val paged : Boolean
) : DomainModel()