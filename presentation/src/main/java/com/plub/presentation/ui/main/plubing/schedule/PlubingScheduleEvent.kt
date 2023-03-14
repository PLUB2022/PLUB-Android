package com.plub.presentation.ui.main.plubing.schedule

import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.ui.Event

sealed class PlubingScheduleEvent : Event {
    data class GoToAddSchedule(val id: Int): PlubingScheduleEvent()
    data class ShowBottomSheetScheduleDetail(val scheduleVo: ScheduleVo): PlubingScheduleEvent()

    data class ShowBottomSheetDialogEditOrDeleteSchedule(val scheduleVo: ScheduleVo): PlubingScheduleEvent()
}
