package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel

data class GetTodoDaysRequestVo(
    val plubbingId: Int = -1,
    val year: Int = -1,
    val month: Int = -1,
) : DomainModel