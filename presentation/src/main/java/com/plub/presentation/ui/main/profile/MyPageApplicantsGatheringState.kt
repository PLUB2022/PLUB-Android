package com.plub.presentation.ui.main.profile

import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.presentation.ui.PageState

data class MyPageApplicantsGatheringState(
    val detailList : List<MyPageDetailVo> = emptyList()
): PageState