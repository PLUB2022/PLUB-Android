package com.plub.data.base

import com.plub.domain.base.DomainModel
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

interface Mapper {
    interface RequestMapper<DTO:DataDto, DOMAIN_MODEL:DomainModel>:Mapper {
        fun mapModelToDto(type: DOMAIN_MODEL):DTO
    }
    interface ResponseMapper<DTO:DataDto, DOMAIN_MODEL:DomainModel>:Mapper {
        fun mapDtoToModel(type: DTO?): DOMAIN_MODEL
    }
}