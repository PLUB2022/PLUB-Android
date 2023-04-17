package com.plub.presentation.ui.main.profile.active.myPost

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyPageAllMyPostState(
    val boardList:StateFlow<List<PlubingBoardVo>>
) : PageState
