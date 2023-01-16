package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.HobbyResponse
import com.plub.domain.model.vo.common.HobbyVo

object HobbiesResponseMapper: Mapper.ResponseMapper<HobbyResponse, HobbyVo> {
    override fun mapDtoToModel(type: HobbyResponse?): HobbyVo {
        return type?.run {
            HobbyVo(
                id = id,
                name = name,
                icon = icon,
                isExpand = false,
                subHobbies = subCategories.map { subHobby ->
                    SubHobbiesResponseMapper.mapDtoToModel(subHobby)
                }
            )
        }?: HobbyVo()
    }
}