package com.plub.data.mapper.recommendationgatheringmapper

import com.plub.data.base.Mapper
import com.plub.data.model.recommendationgatheringdata.RecommendationGatheringData
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

object RecommendationGatheringResponseMapper : Mapper.ResponseMapper<RecommendationGatheringData, RecommendationGatheringResponseVo>{
    override fun mapDtoToModel(type: RecommendationGatheringData?): RecommendationGatheringResponseVo {
        return type?.run {
            RecommendationGatheringResponseVo(
                content.map {
                    //리스트 매핑
                    RecommendationGatheringContentMapper.mapDtoToModel(it)
                } ,
                empty, first, last, number, numberOfElements, size, totalElements, totalPages)

        }  ?: RecommendationGatheringResponseVo(emptyList(), false,false,false,0,0,0,0,0)
    }
}