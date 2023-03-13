package com.plub.presentation.ui.main.plubing.board.pin

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState

data class BoardPinPageState(
    val plubingName:String = "",
    val pinList:List<PlubingBoardVo> = emptyList(),
) : PageState