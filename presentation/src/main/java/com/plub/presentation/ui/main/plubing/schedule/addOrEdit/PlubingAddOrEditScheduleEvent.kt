package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import com.plub.presentation.ui.Event

sealed class PlubingAddOrEditScheduleEvent : Event {
    data class ShowStartDatePickerEventOrEdit(val onClickOk: (year: Int, month: Int, day: Int) -> Unit): PlubingAddOrEditScheduleEvent()
    data class ShowEndDatePickerEventOrEdit(val onClickOk: (year: Int, month: Int, day: Int) -> Unit): PlubingAddOrEditScheduleEvent()
    data class ShowStartTimePickerEventOrEdit(val onClickOk: (hour: Int, minute: Int) -> Unit): PlubingAddOrEditScheduleEvent()
    data class ShowEndTimePickerEventOrEdit(val onClickOk: (hour: Int, minute: Int) -> Unit): PlubingAddOrEditScheduleEvent()
    object ShowBottomSheetSearchLocation: PlubingAddOrEditScheduleEvent()
    object ShowBottomSheetDialogSelectAlarm: PlubingAddOrEditScheduleEvent()
    object GoToBack : PlubingAddOrEditScheduleEvent()
    data class GoToOrEditSchedule(val plubbingId: Int): PlubingAddOrEditScheduleEvent()
}
