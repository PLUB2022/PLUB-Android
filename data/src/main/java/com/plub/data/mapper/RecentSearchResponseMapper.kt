package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.entity.RecentSearchEntity
import com.plub.domain.model.vo.home.search.RecentSearchVo

object RecentSearchResponseMapper: Mapper.ResponseMapper<List<RecentSearchEntity>, List<RecentSearchVo>> {
    override fun mapDtoToModel(type: List<RecentSearchEntity>?): List<RecentSearchVo> {
        return type?.map {
            RecentSearchVo(
                it.search,
                it.saveTime
            )
        }?: emptyList()
    }
}