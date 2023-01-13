package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recommendationgatheringdata.RecommendationGatheringData
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo

object RecommendationGatheringPlubbingsMapper : Mapper.ResponseMapper<RecommendationGatheringData, RecommendationGatheringDataResponseVo>{
    override fun mapDtoToModel(type: RecommendationGatheringData?): RecommendationGatheringDataResponseVo {
        return type?.run {
            RecommendationGatheringDataResponseVo(
                content.map {
                    //리스트 매핑
                    RecommendationGatheringContentMapper.mapDtoToModel(it)
                } ,
                empty, first, last, number, numberOfElements, size, totalElements, totalPages)

        }  ?: RecommendationGatheringDataResponseVo(emptyList(), false,false,false,0,0,0,0,0)
    }
}