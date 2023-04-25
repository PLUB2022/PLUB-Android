package com.plub.presentation.ui.main.profile.active

import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ActiveGatheringPageState(
    val detailList : StateFlow<List<MyPageActiveDetailVo>>
) : PageState
