package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.model.categorylistdata.CategoryListData
import com.plub.domain.model.vo.home.CategoryListResponseVo

import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListResponseMapper : Mapper.ResponseMapper<CategoryListData, CategoryListResponseVo>{
    override fun mapDtoToModel(type: CategoryListData?): CategoryListResponseVo {
        return type?.run {
            CategoryListResponseVo(
                CategoryListDataResponseVo(
                    categories.map {
                        CategoriesDataResponseMapper.mapDtoToModel(it)
                    })
            )
        }!!
    }
}