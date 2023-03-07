package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.SubHobbiesResponse
import com.plub.domain.model.vo.common.SubHobbyVo

object SubHobbyResponseMapper: Mapper.ResponseMapper<SubHobbiesResponse, List<SubHobbyVo>> {
    override fun mapDtoToModel(type: SubHobbiesResponse?): List<SubHobbyVo> {
        return type?.run {
            categories.map {
                SubHobbiesResponseMapper.mapDtoToModel(it)
            }
        }?: emptyList()
    }
}