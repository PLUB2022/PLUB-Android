package com.plub.data.mapper
import com.plub.data.mapper.CategoriesDataResponseMapper
import com.plub.data.base.Mapper
import com.plub.data.dto.categorylistdata.CategoryListData
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListDataResponseMapper : Mapper.ResponseMapper<CategoryListData, CategoryListDataResponseVo> {
    override fun mapDtoToModel(type: CategoryListData?): CategoryListDataResponseVo {
        return type?.run {
            CategoryListDataResponseVo(
                categories = categories.map {
                    CategoriesDataResponseMapper.mapDtoToModel(it)
                }
            )
        }?: CategoryListDataResponseVo(emptyList())
    }
}