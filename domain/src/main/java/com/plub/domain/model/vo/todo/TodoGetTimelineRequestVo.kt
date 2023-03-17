package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.TodoItemViewType

data class TodoGetTimelineRequestVo(
    val todoItemViewType: TodoItemViewType = TodoItemViewType.PLUBING,
    val plubbingId: Int = -1,
    val cursorId: Int = -1
) : DomainModel