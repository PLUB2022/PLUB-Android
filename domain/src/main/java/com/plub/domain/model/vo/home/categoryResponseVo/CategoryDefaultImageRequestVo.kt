package com.plub.domain.model.vo.home.categoryResponseVo

import com.plub.domain.model.DomainModel

data class CategoryDefaultImageRequestVo(
    val categoryId : Int,
    val subCategoryId : Int
) : DomainModel