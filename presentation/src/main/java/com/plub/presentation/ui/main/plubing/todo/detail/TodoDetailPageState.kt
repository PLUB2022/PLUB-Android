package com.plub.presentation.ui.main.plubing.todo.detail

import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class TodoDetailPageState(
    val todoList: StateFlow<List<TodoItemVo>>,

    val profileImage: StateFlow<String>,
    val date: StateFlow<String>,
    val nickname: StateFlow<String>,
    val likeCount: StateFlow<String>,
    val isLike: StateFlow<Boolean>,
) : PageState