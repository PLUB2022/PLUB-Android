package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel

data class GetBoardFeedsRequestVo(
    val page:Int = -1,
    val plubbingId:Int = -1
): DomainModel