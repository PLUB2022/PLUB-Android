package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel

data class GetOtherTodoRequestVo(
    val plubbingId : Int,
    val accountId : Int,
    val cursorId : Int
) : DomainModel
