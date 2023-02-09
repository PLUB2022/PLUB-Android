package com.plub.domain.model.vo.home.categorylistresponsevo

import com.plub.domain.model.DomainModel

data class CategoriesDataResponseVo(
    val id : Int = 0,
    val name : String = "",
    val icon : String = "",
) : DomainModel