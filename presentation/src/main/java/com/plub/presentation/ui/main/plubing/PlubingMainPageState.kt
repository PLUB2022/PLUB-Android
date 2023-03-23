package com.plub.presentation.ui.main.plubing

import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class PlubingMainPageState(
    val headerAlpha: StateFlow<Float>,
    val plubingName: StateFlow<String>,
    val plubingDate: StateFlow<String>,
    val plubingLocation: StateFlow<String>,
    val plubingMainImage: StateFlow<String>,
    val pageType: StateFlow<PlubingMainPageType>,
    val memberList: StateFlow<List<PlubingMemberInfoVo>>
) : PageState