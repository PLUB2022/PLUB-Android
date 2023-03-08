package com.plub.presentation.ui.main.plubing.schedule

import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.ui.PageState

data class PlubingSchedulePageState(
    val plubbingId: Int = -1,
    val scheduleList:List<ScheduleVo> = emptyList(),
    val scheduleCursorId: Int = 0
) : PageState