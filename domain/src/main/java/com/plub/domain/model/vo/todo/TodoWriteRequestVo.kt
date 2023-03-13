package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel

data class TodoWriteRequestVo(
    val plubbingId: Int = -1,
    val todoId: Int = -1,
    val content: String = "",
    val date: String = ""
) : DomainModel