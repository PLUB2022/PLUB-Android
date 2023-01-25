package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringResponseVo(
    val content : List<RecommendationGatheringResponseContentListVo>,
    val empty : Boolean,
    val first : Boolean,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
    //val pageable : RecommendationGatheringDataPageable,
    val size : Int,
    //val sort : RecommendationGatheringDataSort,
    val totalElements : Int,
    val totalPages : Int,
) : DomainModel()