package com.plub.presentation.ui.main.plubing.board.pin

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class BoardPinPageState(
    val plubingName: StateFlow<String>,
    val pinList: StateFlow<List<PlubingBoardVo>>,
) : PageState