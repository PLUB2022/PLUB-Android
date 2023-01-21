package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.SubHobbyResponse
import com.plub.data.entity.RecentSearchEntity
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.search.RecentSearchVo

object RecentSearchRequestMapper: Mapper.RequestMapper<RecentSearchEntity, RecentSearchVo> {

    override fun mapModelToDto(type: RecentSearchVo): RecentSearchEntity {
        return type.run {
            RecentSearchEntity(
                id = id,
                search = search,
                saveTime = saveTime
            )
        }
    }
}