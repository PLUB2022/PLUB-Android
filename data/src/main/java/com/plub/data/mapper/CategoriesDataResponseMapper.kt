package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.categorylistdata.CategoriesData
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo

object CategoriesDataResponseMapper: Mapper.ResponseMapper<CategoriesData, CategoriesDataResponseVo> {
    override fun mapDtoToModel(type: CategoriesData?): CategoriesDataResponseVo {
        return type?.run {
            CategoriesDataResponseVo(
                id = id,
                name = name,
                icon = icon
            )
        }?: CategoriesDataResponseVo()
    }
}