package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recommendationgatheringdata.RecommendationGatheringDataContentList
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo

object RecommendationGatheringContentMapper: Mapper.ResponseMapper<RecommendationGatheringDataContentList, RecommendationGatheringResponseContentListVo> {
    override fun mapDtoToModel(type: RecommendationGatheringDataContentList?): RecommendationGatheringResponseContentListVo {
        return type?.run {
            RecommendationGatheringResponseContentListVo(
                plubbingId = this.plubbingId,
                name = this.name,
                title = this.title,
                introduce = this.introduce,
                time = this.time,
                days = this.days,
                address = this.address,
                roadAddress = this.roadAddress,
                placeName = this.placeName,
                remainAccountNum = this.remainAccountNum,
                curAccountNum = this.curAccountNum,
                isBookmarked = this.isBookmarked
            )
        }?: RecommendationGatheringResponseContentListVo(0,"","","", "", emptyList(),"","", "", 0, 0, false)
    }
}