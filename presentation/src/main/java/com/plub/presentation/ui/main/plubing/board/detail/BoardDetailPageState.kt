package com.plub.presentation.ui.main.plubing.board.detail

import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class BoardDetailPageState(
    val plubingName: StateFlow<String>,
    val profileImage: StateFlow<String>,
    val editCommentVisibility: StateFlow<Boolean>,
    val replyWritingVisibility: StateFlow<Boolean>,
    val replyWritingText: StateFlow<String>,
    val boardVo: StateFlow<PlubingBoardVo>,
    val commentText: MutableStateFlow<String>,
    val commentList: StateFlow<List<BoardCommentVo>>,
) : PageState