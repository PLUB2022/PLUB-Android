package com.plub.data.mapper
import com.plub.data.base.Mapper
import com.plub.data.dto.categorylistdata.CategoryListDataResponse
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListDataResponseMapper : Mapper.ResponseMapper<CategoryListDataResponse, CategoryListDataResponseVo> {
    override fun mapDtoToModel(type: CategoryListDataResponse?): CategoryListDataResponseVo {
        return type?.run {
            CategoryListDataResponseVo(
                categories = categories.map {
                    CategoriesDataResponseMapper.mapDtoToModel(it)
                }
            )
        }?: CategoryListDataResponseVo(emptyList())
    }
}