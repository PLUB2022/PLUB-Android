package com.plub.presentation.ui.main.plubing.schedule.add

import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState

data class PlubingAddSchedulePageState(
    val scheduleTitle: String = "",
    val isAllDay: Boolean = false,
    val month: Int = 1,
    val day: Int = 1,
    val hour: Int = 1,
    val minute: Int = 1,
    val location: String = "",
    val alarm: String = "",
    val memo: String = ""
) : PageState