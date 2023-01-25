package com.plub.data.model

import com.plub.data.base.DataDto

data class CategoriesGatheringRequest (
    val categoryId : Int,
    val pageNumber : Int,
    val accessToken : String,
) : DataDto