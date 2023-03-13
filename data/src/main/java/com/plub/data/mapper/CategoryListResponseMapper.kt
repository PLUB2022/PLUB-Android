package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.categoryListData.CategoryListResponse
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo

object CategoryListResponseMapper :
    Mapper.ResponseMapper<CategoryListResponse, CategoryListDataResponseVo> {
    override fun mapDtoToModel(type: CategoryListResponse?): CategoryListDataResponseVo {
        return type?.run {
            CategoryListDataResponseVo(
                categories = categories.map {
                    CategoriesDataResponseMapper.mapDtoToModel(it)
                })
        } ?: CategoryListDataResponseVo()
    }
}