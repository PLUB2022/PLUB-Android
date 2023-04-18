package com.plub.presentation.ui.main.profile

import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyPageApplicantsGatheringState(
    val detailList : StateFlow<List<MyPageDetailVo>>
): PageState