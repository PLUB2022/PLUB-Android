package com.plub.data.base

import com.plub.domain.base.DomainModel

abstract class Mapper<ENTITY:DataEntity, DOMAIN_MODEL:DomainModel> {
    abstract fun mapFromEntity(type: ENTITY):DOMAIN_MODEL
}