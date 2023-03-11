package com.plub.presentation.ui.main.home.profile

import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.ui.PageState

data class MyPageState(
    val myPageGatheringList : List<MyPageGatheringVo> = List<MyPageGatheringVo>(4){
        MyPageGatheringVo()
    },
    val isReadMore : Boolean = false,
    val isVisible : Boolean = false,
) : PageState
