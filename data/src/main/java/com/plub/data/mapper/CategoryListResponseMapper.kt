package com.plub.data.mapper

import android.util.Log
import com.plub.data.base.Mapper
import com.plub.data.model.CategoryListResponse
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

object CategoryListResponseMapper : Mapper.ResponseMapper<CategoryListResponse, CategoryListResponseVo>{
    override fun mapDtoToModel(type: CategoryListResponse?): CategoryListResponseVo {
        return type?.run {
            Log.d("매퍼 테그", type.toString())
            CategoryListResponseVo(
                //statusCode,
                CategoryListDataResponseMapper.mapDtoToModel(data)

            )
        }!!
    }
}