package com.plub.data.base

import com.plub.domain.base.DomainModel

abstract class Mapper<DTO:DataDto, DOMAIN_MODEL:DomainModel> {
    abstract fun mapDtoToModel(type: DTO):DOMAIN_MODEL
    abstract fun mapModelToDto(type: DOMAIN_MODEL):DTO
}