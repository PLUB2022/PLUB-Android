package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel

data class TodoTimelineListVo(
    val content: List<TodoTimelineVo> = emptyList(),
    val last: Boolean = false,
) : DomainModel