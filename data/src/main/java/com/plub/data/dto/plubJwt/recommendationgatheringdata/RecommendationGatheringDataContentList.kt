package com.plub.data.dto.plubJwt.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataContentList (
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
    val curAccountNum : Int,
    val remainAccountNum: Int,
    val isBookmarked : Boolean
) : DataDto