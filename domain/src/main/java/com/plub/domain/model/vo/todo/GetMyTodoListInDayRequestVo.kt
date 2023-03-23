package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel

data class GetMyTodoListInDayRequestVo(
    val plubbingId: Int = -1,
    val date: String = ""
) : DomainModel