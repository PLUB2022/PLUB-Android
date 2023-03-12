package com.plub.presentation.ui.main.home.profile.active

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState

data class ActiveGatheringPageState(
    val boardList:List<PlubingBoardVo> = emptyList(),
) : PageState
