package com.plub.presentation.ui.main.profile.active.myTodo

import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyPageAllMyTodoState(
    val todoList: StateFlow<List<TodoTimelineVo>>
) : PageState
