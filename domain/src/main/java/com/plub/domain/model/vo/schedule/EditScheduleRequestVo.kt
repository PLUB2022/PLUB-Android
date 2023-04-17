package com.plub.domain.model.vo.schedule

import com.plub.domain.model.DomainModel

data class EditScheduleRequestVo(
    val plubbingId: Int,
    val calendarId: Int,
    val title: String,
    val memo: String,
    val startedAt: String,
    val endedAt: String,
    val startTime: String?,
    val endTime: String?,
    val isAllDay: Boolean,
    val address: String?,
    val roadAddress: String?,
    val placeName: String?,
    val alarmType: String
): DomainModel
