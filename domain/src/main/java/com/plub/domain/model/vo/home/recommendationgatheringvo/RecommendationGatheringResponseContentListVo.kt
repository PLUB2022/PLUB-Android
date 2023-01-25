package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.base.DomainModel

data class RecommendationGatheringResponseContentListVo(
    val plubbingId : Int,
    val name : String,
    val title : String,
    val mainImage : String,
    val introduce : String,
    val time : String,
    val days : List<String>,
    val address : String,
    val roadAddress : String,
    val placeName : String,
    val placePositionX : Double,
    val placePositionY : Double,
    val remainAccountNum: Int,
    val curAccountNum : Int,
    val isBookmarked : Boolean
) : DomainModel()