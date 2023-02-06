package com.plub.presentation.ui.main.plubing

import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState

data class PlubingMainPageState(
    val headerAlpha:Float = 1f,
    val plubingName:String = "",
    val plubingDate:String = "",
    val plubingLocation:String = "",
    val plubingMainImage:String = "",
    val memberList:List<PlubingMemberInfoVo> = emptyList(),
): PageState