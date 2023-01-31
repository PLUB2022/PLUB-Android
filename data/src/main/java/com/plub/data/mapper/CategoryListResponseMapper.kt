package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.categorylistdata.CategoryListDataResponse
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListResponseMapper : Mapper.ResponseMapper<CategoryListDataResponse, CategoryListResponseVo>{
    override fun mapDtoToModel(type: CategoryListDataResponse?): CategoryListResponseVo {
        return type?.run {
            CategoryListResponseVo(
                CategoryListDataResponseVo(
                    categories.map {
                        CategoriesDataResponseMapper.mapDtoToModel(it)
                    })
            )
        }?: CategoryListResponseVo()
    }
}