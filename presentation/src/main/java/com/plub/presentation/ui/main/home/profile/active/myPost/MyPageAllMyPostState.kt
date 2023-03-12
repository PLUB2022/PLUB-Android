package com.plub.presentation.ui.main.home.profile.active.myPost

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState

data class MyPageAllMyPostState(
    val boardList:List<PlubingBoardVo> = emptyList()
) : PageState
