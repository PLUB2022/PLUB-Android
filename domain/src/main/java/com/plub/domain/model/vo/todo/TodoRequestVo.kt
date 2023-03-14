package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.TodoItemViewType

data class TodoRequestVo(
    val plubbingId: Int = -1,
    val timelineId: Int = -1,
    val todoId: Int = -1,
) : DomainModel