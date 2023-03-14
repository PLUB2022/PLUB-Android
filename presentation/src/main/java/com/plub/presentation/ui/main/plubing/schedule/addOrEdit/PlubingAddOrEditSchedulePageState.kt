package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.TimeFormatter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.Calendar

data class PlubingAddSchedulePageState(
    val plubbingId: Int = -1,
    val scheduleTitle: String = "",
    val isAllDay: Boolean = false,
    val startDate: Date = Date(),
    val startTime: Time = Time(),
    val endDate: Date = Date(),
    val endTime: Time = Time(),
    val location: KakaoLocationInfoDocumentVo = KakaoLocationInfoDocumentVo(),
    val alarm: DialogCheckboxItemType = DialogCheckboxItemType.ALARM_NONE,
    val memo: String = ""
) : PageState {
    val isNextButtonEnabled = scheduleTitle.isNotEmpty()
    val startTimeInMills: Long
        get() {
        val (year, month, day) = startDate
        val (hour, minute) = startTime
        val localDate = LocalDateTime.of(year, month, day, hour, minute)
        return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    val endTimeInMills: Long
        get() {
            val (year, month, day) = endDate
            val (hour, minute) = endTime
            val localDate = LocalDateTime.of(year, month, day, hour, minute)
            return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
}

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