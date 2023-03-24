package com.plub.domain.model.vo.schedule

import com.plub.domain.model.DomainModel

data class DeleteScheduleRequestVo(
    val plubbingId: Int,
    val calendarId: Int
): DomainModel
