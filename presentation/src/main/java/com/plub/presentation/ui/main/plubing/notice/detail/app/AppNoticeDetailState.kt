package com.plub.presentation.ui.main.plubing.notice.detail.app

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class AppNoticeDetailState(
    val noticeTitle: StateFlow<String>,
    val noticeDate: StateFlow<String>,
    val noticeContent: StateFlow<String>,
): PageState