package com.plub.data.model.categorylistdata

import com.plub.data.base.DataDto
import com.plub.domain.base.DomainModel

data class CategoriesData(
    val id : Int,
    val name : String,
    val icon : String
): DataDto
