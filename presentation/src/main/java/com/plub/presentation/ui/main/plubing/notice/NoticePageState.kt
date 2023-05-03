package com.plub.presentation.ui.main.plubing.notice

import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class NoticePageState(
    val noticeList:StateFlow<List<NoticeVo>>,
    val plubingName:StateFlow<String>
): PageState