package com.plub.presentation.ui.main.profile

import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyPageState(
    val myPageGatheringList : StateFlow<List<MyPageGatheringVo>>,
    val isReadMore : StateFlow<Boolean>,
    val myName : StateFlow<String>,
    val myIntro : StateFlow<String>,
    val profileImage : StateFlow<String>,
    val isEmpty : StateFlow<Boolean>
) : PageState
