package com.plub.presentation.ui.main.plubing.todo

import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class PlubingTodoPageState(
    val todoList:StateFlow<List<TodoTimelineVo>>
): PageState