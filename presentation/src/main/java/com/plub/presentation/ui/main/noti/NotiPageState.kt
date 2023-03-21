package com.plub.presentation.ui.main.noti

import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class NotiPageState(
    val notiList:StateFlow<List<NotificationResponseVo>>
): PageState