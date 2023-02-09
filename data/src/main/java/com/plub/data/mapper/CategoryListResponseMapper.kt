package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.categorylistdata.CategoryListResponse
import com.plub.domain.model.enums.HomeCategoryViewType
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListResponseMapper :
    Mapper.ResponseMapper<CategoryListResponse, CategoryListDataResponseVo> {
    override fun mapDtoToModel(type: CategoryListResponse?): CategoryListDataResponseVo {
        return type?.run {
            CategoryListDataResponseVo(
                viewType = HomeCategoryViewType.CATEGORY_VIEW,
                categories = categories.map {
                    CategoriesDataResponseMapper.mapDtoToModel(it)
                })
        } ?: CategoryListDataResponseVo()
    }
}