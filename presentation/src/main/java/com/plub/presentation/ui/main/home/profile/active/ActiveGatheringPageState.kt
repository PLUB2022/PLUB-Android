package com.plub.presentation.ui.main.home.profile.active

import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.presentation.ui.PageState

data class ActiveGatheringPageState(
    val detailList : List<MyPageActiveDetailVo> = emptyList(),
) : PageState
