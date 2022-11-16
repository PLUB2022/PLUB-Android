package com.plub.data.base

import com.plub.domain.base.DomainModel

interface Mapper {
    interface RequestMapper<DTO:DataDto, DOMAIN_MODEL:DomainModel>:Mapper {
        fun mapModelToDto(type: DOMAIN_MODEL):DTO
    }
    interface ResponseMapper<DTO:DataDto, DOMAIN_MODEL:DomainModel>:Mapper {
        fun mapDtoToModel(type: DTO?):DOMAIN_MODEL
    }
}