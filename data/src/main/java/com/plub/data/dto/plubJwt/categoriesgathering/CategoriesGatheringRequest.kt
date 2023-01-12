package com.plub.data.model.categoriesgathering

data class CategoriesGatheringRequest (
    val categoryId : Int,
    val pageNumber : Int,
    val accessToken : String,
)