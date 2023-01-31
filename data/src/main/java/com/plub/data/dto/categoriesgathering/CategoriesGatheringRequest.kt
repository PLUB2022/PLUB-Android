package com.plub.data.dto.categoriesgathering

import com.plub.data.base.DataDto

data class CategoriesGatheringRequest (
    val categoryId : Int = 0,
    val pageNumber : Int = 0,
) : DataDto