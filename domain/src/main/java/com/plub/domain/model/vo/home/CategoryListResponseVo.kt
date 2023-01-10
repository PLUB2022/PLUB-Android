package com.plub.domain.model.vo.home

import com.plub.domain.base.DomainModel
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

data class CategoryListResponseVo(
    //val statusCode: Int,
    val data: CategoryListDataResponseVo
) : DomainModel()
