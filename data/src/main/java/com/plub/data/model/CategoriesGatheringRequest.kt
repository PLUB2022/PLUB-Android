package com.plub.data.model

data class CategoriesGatheringRequest (
    val categoryId : Int,
    val pageNumber : Int,
    val accessToken : String,
)