package com.plub.presentation.ui.main.plubing.todo

import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState

data class PlubingTodoPageState(
    val todoList:List<TodoTimelineVo> = emptyList(),
): PageState