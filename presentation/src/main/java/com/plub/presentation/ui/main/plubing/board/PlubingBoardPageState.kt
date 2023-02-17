package com.plub.presentation.ui.main.plubing.board

import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.ui.PageState

data class PlubingBoardPageState(
    val clipBoardList:List<PlubingBoardVo> = emptyList(),
    val boardList:List<PlubingBoardVo> = emptyList(),
): PageState