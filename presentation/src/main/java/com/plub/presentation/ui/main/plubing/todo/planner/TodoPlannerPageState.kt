package com.plub.presentation.ui.main.plubing.todo.planner

import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class TodoPlannerPageState(
    val plubingName: StateFlow<String>,
    val todoDays: StateFlow<List<Int>>,
    val todoList: StateFlow<List<TodoItemVo>>,
    val calendarDateText: StateFlow<String>,
    val todoDateText: StateFlow<String>,
    val isToday: StateFlow<Boolean>,
    val isTodoEditMode: StateFlow<Boolean>,
    val todoText: StateFlow<String>,
) : PageState