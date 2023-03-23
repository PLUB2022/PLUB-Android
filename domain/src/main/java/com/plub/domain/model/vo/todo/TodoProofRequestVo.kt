package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.TodoItemViewType

data class TodoProofRequestVo(
    val plubbingId: Int = -1,
    val todoListId: Int = -1,
    val proofImageUrl: String = "",
) : DomainModel