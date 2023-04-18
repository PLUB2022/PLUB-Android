package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel

data class MyPageActiveRequestVo(
    val plubbingId : Int,
    val cursorId : Int
) : DomainModel
