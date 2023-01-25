package com.plub.data.model.recommendationgatheringdata

data class RecommendationGatheringData(
    //val content : Content~~
    val empty : Boolean,
    val first : Boolean,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
    //val pageable : PageAble~~
    val size : Int,
    //val sort : Sort~~
    val totalElements : Int,
    val totalPages : Int,
)
