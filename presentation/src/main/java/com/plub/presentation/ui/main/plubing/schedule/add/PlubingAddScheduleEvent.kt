package com.plub.presentation.ui.main.plubing.schedule.add

import com.plub.presentation.ui.Event

sealed class PlubingAddScheduleEvent : Event {
    data class ShowStartDatePickerEvent(val onClickOk: (year: Int, month: Int, day: Int) -> Unit): PlubingAddScheduleEvent()
    data class ShowEndDatePickerEvent(val onClickOk: (year: Int, month: Int, day: Int) -> Unit): PlubingAddScheduleEvent()
    data class ShowStartTimePickerEvent(val onClickOk: (hour: Int, minute: Int) -> Unit): PlubingAddScheduleEvent()
    data class ShowEndTimePickerEvent(val onClickOk: (hour: Int, minute: Int) -> Unit): PlubingAddScheduleEvent()
}
