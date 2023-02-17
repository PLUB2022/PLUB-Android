package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plub.PlubingAccountInfoResponse
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo

object PlubingAccountInfoResponseMapper : Mapper.ResponseMapper<PlubingAccountInfoResponse, PlubingMemberInfoVo> {
    override fun mapDtoToModel(type: PlubingAccountInfoResponse?): PlubingMemberInfoVo {
        return type?.run {
            PlubingMemberInfoVo(
                id = id,
                nickname = nickname,
                profileImage = profileImage,
            )
        } ?: PlubingMemberInfoVo()
    }
}