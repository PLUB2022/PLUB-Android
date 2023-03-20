package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.TimeFormatter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.Calendar

data class PlubingAddOrEditSchedulePageState(
    val plubbingId: Int = -1,
    val calendarId: Int = -1,
    val scheduleTitle: String = "",
    val isAllDay: Boolean = false,
    val startScheduleDate: ScheduleDate = ScheduleDate(),
    val startScheduleTime: ScheduleTime = ScheduleTime(),
    val endScheduleDate: ScheduleDate = ScheduleDate(),
    val endScheduleTime: ScheduleTime = ScheduleTime(),
    val location: KakaoLocationInfoDocumentVo = KakaoLocationInfoDocumentVo(),
    val alarm: DialogCheckboxItemType = DialogCheckboxItemType.ALARM_NONE,
    val memo: String = "",
    val isEditMode: Boolean = false
) : PageState {
    val isNextButtonEnabled = scheduleTitle.isNotEmpty()
    val startTimeInMills: Long
        get() {
        val (year, month, day) = startScheduleDate
        val (hour, minute) = startScheduleTime
        val localDate = LocalDateTime.of(year, month, day, hour, minute)
        return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    val endTimeInMills: Long
        get() {
            val (year, month, day) = endScheduleDate
            val (hour, minute) = endScheduleTime
            val localDate = LocalDateTime.of(year, month, day, hour, minute)
            return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
}

data class ScheduleDate(
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
    val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
) {
    constructor(list: List<Int>) : this(list[0], list[1], list[2])
    val text = TimeFormatter.getyyyydotMMdotddE(year, month, day)
}

data class ScheduleTime(
    val hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    val minute: Int = Calendar.getInstance().get(Calendar.MINUTE)
) {
    constructor(list: List<Int>) : this(list[0], list[1])
    val text = TimeFormatter.getAmPmHourColonMin(hour, minute)
}