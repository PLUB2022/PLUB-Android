package com.plub.presentation.ui.main.plubing.board

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class PlubingBoardPageState(
    val clipBoardList: StateFlow<List<PlubingBoardVo>>,
    val boardList: StateFlow<List<PlubingBoardVo>>,
) : PageState