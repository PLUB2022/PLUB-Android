package com.plub.presentation.ui.main.plubing.schedule

import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState

data class PlubingSchedulePageState(
    val headerAlpha: Float = 1f,
    val plubingName: String = "",
    val plubingDate: String = "",
    val plubingLocation: String = "",
    val plubingMainImage: String = "",
    val pageType: PlubingMainPageType = PlubingMainPageType.BOARD,
    val memberList: List<PlubingMemberInfoVo> = emptyList(),
) : PageState