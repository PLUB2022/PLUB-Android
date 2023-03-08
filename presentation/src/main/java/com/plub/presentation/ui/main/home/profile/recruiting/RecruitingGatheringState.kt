package com.plub.presentation.ui.main.home.profile.recruiting

import com.plub.domain.model.vo.home.myPage.MyPageDetailVo
import com.plub.presentation.ui.PageState

data class RecruitingGatheringState(
    val detailList : List<MyPageDetailVo> = emptyList()
): PageState