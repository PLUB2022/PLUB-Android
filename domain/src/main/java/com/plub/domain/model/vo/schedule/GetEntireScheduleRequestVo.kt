package com.plub.domain.model.vo.schedule

import com.plub.domain.model.DomainModel

data class GetEntireScheduleRequestVo(
    val cursorId:Int = -1,
    val plubbingId:Int = -1
): DomainModel
