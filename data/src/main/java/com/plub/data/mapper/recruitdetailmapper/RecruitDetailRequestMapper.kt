package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringRequest
import com.plub.data.dto.recruitdetail.RecruitDetailRequest
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo

object RecruitDetailRequestMapper {
    fun mapDtoToModel(type: RecruitDetailRequestVo?): RecruitDetailRequest {
        return type?.run {
            RecruitDetailRequest(
                plubbingId = this.plubbingId)
        }  ?: RecruitDetailRequest(0)
    }
}