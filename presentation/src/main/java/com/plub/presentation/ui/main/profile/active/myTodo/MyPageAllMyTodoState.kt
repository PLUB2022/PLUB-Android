package com.plub.presentation.ui.main.profile.active.myTodo

import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState

data class MyPageAllMyTodoState(
    val todoList: List<TodoTimelineVo> = emptyList(),
) : PageState
