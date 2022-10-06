package com.plub.data.base

import com.plub.domain.UiState

interface Mapper<ENTITY, DOMAIN_MODEL> {
    fun mapFromEntity(type: ENTITY): UiState<DOMAIN_MODEL>
}