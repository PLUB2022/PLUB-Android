package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.hobby.SubHobbyResponse
import com.plub.domain.model.vo.common.SubHobbyVo

object SubHobbiesResponseMapper: Mapper.ResponseMapper<SubHobbyResponse, SubHobbyVo> {
    override fun mapDtoToModel(type: SubHobbyResponse?): SubHobbyVo {
        return type?.run {
            SubHobbyVo(
                id = id,
                name = name,
                parentHobbyId = parentId,
            )
        }?: SubHobbyVo()
    }
}