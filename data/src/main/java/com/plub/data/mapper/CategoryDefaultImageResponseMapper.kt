package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.CategoryImageResponse
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageResponseVo

object CategoryDefaultImageResponseMapper :
    Mapper.ResponseMapper<CategoryImageResponse, CategoryDefaultImageResponseVo> {
    override fun mapDtoToModel(type: CategoryImageResponse?): CategoryDefaultImageResponseVo {
        return type?.run {
            CategoryDefaultImageResponseVo(
                image = image
            )
        } ?: CategoryDefaultImageResponseVo()
    }
}