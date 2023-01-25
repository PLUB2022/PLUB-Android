package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringRequest
import com.plub.data.model.recruitdetail.RecruitDetailRequest
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo

object RecruitDetailRequestMapper {
    fun mapDtoToModel(type: RecruitDetailRequestVo?): RecruitDetailRequest {
        return type?.run {
            RecruitDetailRequest(
                plubbingId, accessToken)
        }  ?: RecruitDetailRequest(0, "")
    }
}