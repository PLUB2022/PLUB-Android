package com.plub.presentation.ui.main.plubing.board.detail

import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState

data class BoardDetailPageState(
    val plubingName: String = "",
    val profileImage: String = "",
    val editCommentVisibility: Boolean = false,
    val replyWritingVisibility: Boolean = false,
    val replyWritingText: String = "",
    val boardVo: PlubingBoardVo = PlubingBoardVo(),
    var commentText:String = "",
    val commentList: List<BoardCommentVo> = emptyList(),
) : PageState