package com.plub.domain.model.vo.myGathering

import com.plub.domain.model.DomainModel

data class KickOutRequestVo(
    val plubbingId: Int,
    val accountId: Int
): DomainModel