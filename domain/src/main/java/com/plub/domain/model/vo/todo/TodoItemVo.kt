package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.TodoItemViewType

data class TodoItemVo(
    val viewType: TodoItemViewType = TodoItemViewType.PLUBING,
    val todoId: Int = -1,
    val content: String = "",
    val date: String = "",
    val isChecked: Boolean = false,
    val isProof: Boolean = false,
    val proofImage: String = "",
    val likes: Int = -1,
    val isAuthor: Boolean = false
) : DomainModel