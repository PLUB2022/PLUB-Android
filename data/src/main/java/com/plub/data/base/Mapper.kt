package com.plub.data.base

interface Mapper {
    interface RequestMapper<DTO, DOMAIN_MODEL>:Mapper {
        fun mapModelToDto(type: DOMAIN_MODEL):DTO
    }
    interface ResponseMapper<DTO, DOMAIN_MODEL>:Mapper {
        fun mapDtoToModel(type: DTO?):DOMAIN_MODEL
    }
}