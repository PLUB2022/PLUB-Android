package com.plub.presentation.ui.main.plubing.schedule

import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState

data class PlubingSchedulePageState(
    val plubbingId: Int = -1
) : PageState