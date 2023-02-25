package com.plub.presentation.ui.main.plubing.schedule.add

import com.plub.presentation.ui.PageState
import com.plub.presentation.util.TimeFormatter
import java.util.Calendar

data class PlubingAddSchedulePageState(
    val scheduleTitle: String = "",
    val isAllDay: Boolean = false,
    val startDate: Date = Date(),
    val startTime: Time = Time(),
    val endDate: Date = Date(),
    val endTime: Time = Time(),
    val location: String = "",
    val alarm: String = "",
    val memo: String = ""
) : PageState

data class Date(
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
    val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
) {
    val text = TimeFormatter.getyyyydotMMdotddE(year, month, day)
}

data class Time(
    val hour: Int = Calendar.getInstance().get(Calendar.HOUR),
    val minute: Int = Calendar.getInstance().get(Calendar.MINUTE)
) {
    val text = TimeFormatter.getAmPmHourColonMin(hour, minute)
}