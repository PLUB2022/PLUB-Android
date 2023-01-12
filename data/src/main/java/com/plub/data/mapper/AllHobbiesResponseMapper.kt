package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.AllHobbiesResponse
import com.plub.domain.model.vo.common.HobbyVo

object AllHobbiesResponseMapper: Mapper.ResponseMapper<AllHobbiesResponse, List<HobbyVo>> {
    override fun mapDtoToModel(type: AllHobbiesResponse?): List<HobbyVo> {
        return type?.run {
            categories.map {
                HobbiesResponseMapper.mapDtoToModel(it)
            }
        }?: emptyList()
    }
}