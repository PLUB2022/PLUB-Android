package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.dto.plubJwt.recruitdetail.RecruitDetailRequest
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo

object RecruitDetailRequestMapper {
    fun mapDtoToModel(type: RecruitDetailRequestVo?): RecruitDetailRequest {
        return type?.run {
            RecruitDetailRequest(
                plubbingId, accessToken)
        }  ?: RecruitDetailRequest(0, "")
    }
}