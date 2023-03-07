package com.plub.domain.model.vo.home.categoryResponseVo

import com.plub.domain.model.DomainModel

data class CategoriesDataResponseVo(
    val id : Int = 0,
    val name : String = "",
    val icon : String = "",
) : DomainModel