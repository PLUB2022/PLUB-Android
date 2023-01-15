package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recommendationgatheringdata.RecommendationGatheringDataContentList
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo

object RecommendationGatheringContentMapper: Mapper.ResponseMapper<RecommendationGatheringDataContentList, RecommendationGatheringResponseContentListVo> {
    override fun mapDtoToModel(type: RecommendationGatheringDataContentList?): RecommendationGatheringResponseContentListVo {
        return type?.run {
            RecommendationGatheringResponseContentListVo(
                plubbingId, name, title, mainImage, introduce, days, curAccountNum, isBookmarked
            )
        }?: RecommendationGatheringResponseContentListVo(0,"","","","", emptyList(),0,false)
    }
}