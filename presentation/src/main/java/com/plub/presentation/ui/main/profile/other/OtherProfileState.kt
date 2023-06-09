package com.plub.presentation.ui.main.profile.other

import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class OtherProfileState(
    val profile : StateFlow<MyInfoResponseVo>,
    val todoList : StateFlow<List<TodoTimelineVo>>
) : PageState
