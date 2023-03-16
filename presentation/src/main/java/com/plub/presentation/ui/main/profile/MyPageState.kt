package com.plub.presentation.ui.main.profile

import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.ui.PageState

data class MyPageState(
    val myPageGatheringList : List<MyPageGatheringVo> = emptyList(),
    val isReadMore : Boolean = false,
    val myName : String = "",
    val myIntro : String = "",
    val profileImage : String? = ""
) : PageState
